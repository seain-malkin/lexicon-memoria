package com.example.lexicon_memoria.dictionary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class DictionaryRemoteDataSource(
    private val api: DictionaryApi
) {
    suspend fun getWord(word: String, language: String?) : DictionaryWord {
        return flow {
            emit(api.requestWord(word, language))
        }.catch { e ->
            emit(DictionaryWord(e.message ?: "Unkown error"))
        }.single()
    }

    interface DictionaryApi {
        /**
         * Function to be implemented by all dictionary apis.
         * @param[word] The word to find
         * @param[language] The language code. Dictionary can use default if null
         * @return
         */
        suspend fun requestWord(word: String, language: String?) : DictionaryWord
    }
}