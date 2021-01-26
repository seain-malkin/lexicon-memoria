package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.database.dao.DictionaryDao
import com.example.lexicon_memoria.database.entity.*
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.Homograph
import com.example.lexicon_memoria.dictionary.Word
import kotlinx.coroutines.flow.*

class DictionaryRepository(
        private val remote: DictionaryRemoteDataSource,
        private val persistent: DictionaryDao
) {

    suspend fun get(word: String) : Flow<WordWithFunctionalDefinitions?> {
        val dbWord = persistent.get(word)

        // Use DB result if not empty, otherwise check remote source
        val word = when(dbWord.isEmpty()) {
            true -> getRemote(word)
            false -> dbWord.first()
        }

        // TODO: Save word to database

        // Return result as a flow even when null
        return flow { emit(word) }
    }

    private suspend fun getRemote(word: String) : WordWithFunctionalDefinitions? {
        val remWord = remote.search(word)
        // Convert remote response to a DB entity relation
        return when (remWord == null) {
            true -> null
            false -> WordWithFunctionalDefinitions(
                    HeadWordEntity(remWord.head),
                    remWord.homographs.map {
                        FunctionWithDefinitions(
                                SpeechFunctionEntity(remWord.head, it.label),
                                it.definitions.mapIndexed { i, s ->
                                    DefinitionEntity(s, i)
                                }
                        )
                    }
            )
        }
    }
}