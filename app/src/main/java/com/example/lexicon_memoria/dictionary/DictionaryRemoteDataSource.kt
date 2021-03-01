package com.example.lexicon_memoria.dictionary

import com.example.lexicon_memoria.dictionary.merriam_webster.CollegiateApi

class DictionaryRemoteDataSource(
    private val api: CollegiateApi
) {

    /**
     * Searches the remote data source for the key
     * @param[key] The word to search for
     * @return The api result object
     */
    suspend fun find(key: String): DictionaryApiResult {
        return api.find(key)
    }

    /**
     * API responses must implement this interface
     */
    interface DictionaryApiResult {

        var headword: Headword
        var homographs: List<Homograph>
        var pronunciation: Pronunciation?

        data class Headword(
            var name: String,
            var source: String,
            var sourceId: String? = null,
            var sortIndex: String? = null,
        )

        data class Homograph (
            var name: String,
            var definitions: List<String>,
        )

        data class Pronunciation(
            var spoken: String,
            var audio: String? = null
        )
    }
}