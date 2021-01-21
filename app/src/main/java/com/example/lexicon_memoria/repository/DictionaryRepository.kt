package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.database.dao.DictionaryDao
import com.example.lexicon_memoria.database.entity.WordWithFunctionalDefinitions
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.Word
import kotlinx.coroutines.flow.*

class DictionaryRepository(
        private val remote: DictionaryRemoteDataSource,
        private val persistent: DictionaryDao
) {
    fun getWord(word: String) : Flow<Word> {
        return remote.search(word)
    }

    fun get(word: String) : Flow<WordWithFunctionalDefinitions> {
        val dbWord = persistent.get(word)
        if (dbWord.isEmpty()) {
            val remWord = remote.search(word)
            // TODO: Convert remWord to dbWord. Save to database. Return result.
        }
    }
}