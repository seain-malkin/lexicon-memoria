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
    fun search(word: String) : Flow<List<DictionaryWord>> {
        return flow {
            emit(api.search(word))
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
        suspend fun search(word: String) : List<DictionaryWord>
    }
}