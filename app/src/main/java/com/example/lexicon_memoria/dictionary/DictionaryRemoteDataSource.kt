package com.example.lexicon_memoria.dictionary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRemoteDataSource(
    private val api: DictionaryApi
) {
    fun lookup(word: String, language: String?) : Flow<List<DictionaryWord>> {
        return flow {
            emit(api.lookup(word, language))
        }
    }

    interface DictionaryApi {
        /**
         * Function to be implemented by all dictionary apis.
         * @param[word] The word to find
         * @param[language] The language code. Dictionary can use default if null
         * @return
         */
        suspend fun lookup(word: String, language: String?) : List<DictionaryWord>
    }
}