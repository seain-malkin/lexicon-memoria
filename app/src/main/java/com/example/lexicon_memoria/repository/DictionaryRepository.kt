package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.DictionaryWord
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(
        private val remote: DictionaryRemoteDataSource
) {
    fun search(word: String) : Flow<List<DictionaryWord>> {
        return remote.search(word)
    }
}