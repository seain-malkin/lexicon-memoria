package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.DictionaryWord
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(
        private val dataSource: DictionaryRemoteDataSource
) {
    suspend fun getWord(word: String, language: String?) : DictionaryWord {
        return dataSource.getWord(word, language)
    }
}