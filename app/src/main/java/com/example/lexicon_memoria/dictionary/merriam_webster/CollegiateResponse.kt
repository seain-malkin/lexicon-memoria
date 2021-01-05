package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResponse
import com.google.gson.annotations.SerializedName

/**
 * Class used by Retrofit to build service response objects
 * @author Seain Malkin (dev@seain.me)
 */
class CollegiateResponse : DictionaryApiResponse {

    override var text = ""
        get() = headword?.text ?: throw IllegalStateException("headword can't be null")

    override var id = ""
        get() = meta?.uniqueId ?: throw IllegalStateException("meta can't be null")

    @SerializedName("meta")
    var meta: Meta? = null

    @SerializedName("hom")
    override var homograph = 0

    @SerializedName("hwi")
    var headword: Headword? = null

    @SerializedName("fl")
    override var label: String = ""

    @SerializedName("shortdef")
    var _definitions: ArrayList<String> = arrayListOf()
    override val definitions: ArrayList<String> get() = _definitions

    /**
     * @see[DictionaryApiResponse.toString]
     */
    override fun toString(): String {
        return headword.toString()
    }

    /**
     * @see[DictionaryApiResponse]
     */
    override fun equals(other: Any?): Boolean {
        return when(other is CollegiateResponse) {
            true -> meta!!.uniqueId == other.meta!!.uniqueId
            false -> false
        }
    }

    inner class Meta {
        @SerializedName("id")
        var id: String = ""

        @SerializedName("uuid")
        var uniqueId: String = ""
    }

    inner class Headword {

        @SerializedName("hw")
        var text: String = ""

        override fun toString(): String {
            return text
        }
    }

}