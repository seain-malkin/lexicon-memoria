package com.example.lexicon_memoria.dictionary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRemoteDataSource(
    val api: DictionaryApi
) {

    /**
     * Searchs for a word and returns a list of results
     * @param[word] The word to search for
     * @return The api result as a single word object
     */
    suspend fun search(word: String) : Word? {
        val wordList = api.get(word)
        // Return null on empty result or a dictionary word object
        return when (wordList.isEmpty()) {
            true -> null
            false -> Word(wordList[0].text, wordList.filter {
                it.homograph > 0
            }.map {
                Homograph(it.label, it.definitions)
            })
        }
    }

    /**
     * The api dependency must implement this interface
     */
    interface DictionaryApi {

        /**
         * Function to be implemented by all dictionary apis.
         * @param[word] The word to find
         * @return A list of word homographs
         */
        suspend fun get(word: String) : List<DictionaryApiResponse>
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