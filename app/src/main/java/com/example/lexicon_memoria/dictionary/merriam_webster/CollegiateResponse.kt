package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult.Homograph
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult.Headword
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Class used by Retrofit to build service response objects
 * @author Seain Malkin (dev@seain.me)
 */
data class CollegiateResponse(
    override var headword: Headword,
    override var homographs: List<Homograph>
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

        // Build the headword object from the first result
        val headword = buildHeadword(result[0].asJsonObject.get("meta").asJsonObject)

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
            homographs.add(buildHomograph(result[0].asJsonObject))
        }

        return CollegiateResponse(headword, homographs)
    }

    private fun buildHeadword(json: JsonObject): Headword {
        return Headword(
            json.get("id").asString,
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