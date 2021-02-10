package com.example.lexicon_memoria.dictionary

import com.example.lexicon_memoria.database.entity.DictionaryWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRemoteDataSource(
    private val api: DictionaryApi
) {

    /**
     * Searches the remote data source for the key
     * @param[key] The word to search for
     * @return The api result object
     */
    fun find(key: String): DictionaryApiResult {
        return api.find(key)
    }

    /**
     * The api dependency must implement this interface
     */
    interface DictionaryApi {

        /**
         * Function to be implemented by all dictionary apis.
         * @param[key] The word to find
         * @return A formatted result. Throws an exception if not found
         */
        fun find(key: String) : DictionaryApiResult
    }

    /**
     * API responses must implement this interface
     */
    interface DictionaryApiResult {

        var headword: Headword
        var homographs: List<Homograph>

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
    }
}