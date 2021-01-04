package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.Word
import kotlinx.coroutines.flow.Flow

class DictionaryRepository(
        private val remote: DictionaryRemoteDataSource
) {
    fun getWord(word: String) : Flow<Word> {
        return remote.search(word)
    }
}