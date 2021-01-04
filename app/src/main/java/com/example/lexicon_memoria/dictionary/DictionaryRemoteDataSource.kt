package com.example.lexicon_memoria.dictionary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRemoteDataSource(
    private val api: DictionaryApi
) {

    /**
     * Searchs for a word and returns a list of results
     * @param[word] The word to search for
     * @return The list as a flow
     */
    fun search(word: String) : Flow<Word> {
        return flow {
            // Fetch api results
            val results = api.get(word)

            // Transform results into a single word
            emit(DictionaryApi.buildResult(results))
        }
    }

    /**
     * The api dependency must implement this interface
     */
    interface DictionaryApi {

        /**
         * Function to be implemented by all dictionary apis.
         * @param[word] The word to find
         * @return A list of words
         */
        suspend fun get(word: String) : List<DictionaryApiResponse>

        companion object {

            /**
             * Takes a list of responses from the api call and consolidates them into one
             * word object
             * @param[results] The list of responses
             * @return The word object created from the responses
             */
            fun buildResult(results: List<DictionaryApiResponse>): Word {
                return Word(results[0].text, results.map { Homograph(it.text, it.definitions) })
            }
        }
    }

    /**
     * API responses must implement this interface
     */
    interface DictionaryApiResponse {

        /** A unique ID representing the word in the api source */
        var id: String

        /** The text string representing the word */
        var text: String

        /** The homograph index of the homograph list */
        var homograph: Int

        /** The functional label of the word */
        var label: String

        /** An array of definitions of the word */
        val definitions: ArrayList<String>

        /**
         * Determines if this object equals the given one
         * @param[other] The object to compare to
         * @return The equality
         */
        override fun equals(other: Any?) : Boolean
    }
}