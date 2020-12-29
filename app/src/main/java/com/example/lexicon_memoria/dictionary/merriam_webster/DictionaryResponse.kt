package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.google.gson.annotations.SerializedName

/**
 * Class used by Retrofit to build service response objects
 * @author Seain Malkin (dev@seain.me)
 */
class DictionaryResponse : DictionaryWord {

    /**
     * @see[DictionaryWord.toString]
     */
    override fun toString(): String {
        return headword!!.toString()
    }

    /**
     * The meta property exists on every result so assume it's not null
     * @see[DictionaryWord.equals]
     */
    override fun equals(other: Any?): Boolean {
        return when(other is DictionaryResponse) {
            true -> meta!!.uniqueId!! == other.meta!!.uniqueId!!
            false -> false
        }
    }

    @SerializedName("meta")
    var meta: Meta? = null

    @SerializedName("hom")
    var homograph: Int? = null

    @SerializedName("hwi")
    var headword: Headword? = null

    @SerializedName("fl")
    var functionalLabel: String? = null

    @SerializedName("sls")
    var subjectLabel: ArrayList<String>? = null

    @SerializedName("psl")
    var usageLabel: String? = null

    @SerializedName("ins")
    var inflections: ArrayList<Inflection>? = null

    @SerializedName("shortdef")
    var shortDefinition: ArrayList<String>? = null

    inner class Meta {
        @SerializedName("id")
        var id: String? = null

        @SerializedName("uuid")
        var uniqueId: String? = null
    }

    inner class Headword {

        @SerializedName("hw")
        var headword: String? = null

        override fun toString(): String {
            return headword!!
        }
    }

    inner class Inflection {

        @SerializedName("if")
        var full: String? = null

        @SerializedName("ifc")
        var cutback: String? = null

        @SerializedName("il")
        var label: String? = null
    }

}