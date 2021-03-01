package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult.Homograph
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult.Headword
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult.Pronunciation
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Class used by Retrofit to build service response objects
 * @author Seain Malkin (dev@seain.me)
 */
data class CollegiateResponse(
    override var headword: Headword,
    override var homographs: List<Homograph>,
    override var pronunciation: Pronunciation? = null
) : DictionaryApiResult

class CollegiateResponseDeserializer : JsonDeserializer<CollegiateResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CollegiateResponse {
        // Retrieve result array or throw exception
        val result = json?.asJsonArray ?: throw JsonParseException("Invalid response")

        // Ensure at least one result
        if (result.size() == 0) throw JsonParseException("Empty result")

        val firstResult = result[0].asJsonObject

        // Build the headword object from the first result
        val headword = buildHeadword(firstResult.get("meta").asJsonObject)

        // Build a homograph for each result that has the "hom" property
        val homographs = mutableListOf<Homograph>()

        result.forEach {
            val element = it.asJsonObject
            if (element.has("hom")) {
                homographs.add(buildHomograph(element))
            }
        }

        // If the list is still empty, use the first result
        if (homographs.isEmpty()) {
            homographs.add(buildHomograph(firstResult))
        }

        // Extract pronunciation
        val hwi = firstResult.get("hwi").asJsonObject
        var pronunciation: Pronunciation? = null
        if (hwi.has("prs")) {
            val prs = hwi.get("prs").asJsonArray[0].asJsonObject
            pronunciation = Pronunciation(prs.get("mw").asString)
            if (prs.has("sound")) {
                pronunciation.audio = prs.get("sound").asJsonObject.get("audio").asString
            }
        }

        return CollegiateResponse(headword, homographs, pronunciation)
    }

    private fun buildHeadword(json: JsonObject): Headword {
        return Headword(
            json.get("id").asString.substringBefore(':'),
            json.get("src").asString,
            json.get("uuid").asString,
            json.get("sort").asString
        )
    }

    private fun buildHomograph(json: JsonObject): Homograph {
        // Extract the list of short definitions
        val defs = mutableListOf<String>()
        json.get("shortdef").asJsonArray.forEach { defs.add(it.asString) }

        return Homograph(json.get("fl").asString, defs.toList())
    }
}