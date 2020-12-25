package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.DictionaryWord
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(
        private val dataSource: DictionaryRemoteDataSource
) {
    fun lookup(word: String, language: String?) : Flow<List<DictionaryWord>> {
        return dataSource.lookup(word, language)
    }
}