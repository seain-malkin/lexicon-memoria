package com.example.lexicon_memoria.repository

import com.example.lexicon_memoria.database.dao.DictionaryDao
import com.example.lexicon_memoria.database.entity.*
import com.example.lexicon_memoria.database.entity.builder.WordBuilder
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult
import kotlinx.coroutines.flow.*
import java.lang.IllegalStateException
import kotlin.jvm.Throws

/**
 * Retrieves, inserts and modifies word entity objects
 * @property persistent Local persistent storage accessor
 * @property remote Remote data source
 */
class DictionaryRepository(
        private val persistent: DictionaryDao,
        private val remote: DictionaryRemoteDataSource
) {

    /**
     * Searches for a word, first using local persistence, backed by a remote source
     * @param key The word to search for
     * @return The word entity inside a flow
     */
    suspend fun get(key: String) : Flow<DictionaryWord> {
        // Check local storage. Otherwise query remote source, save to local storage and return
        return flow {
            val result = persistent.find(key)

            val word = when(result != null) {
                true -> result
                false -> transformResult(remote.find(key)).apply { persistent.save(this) }
            }

            emit(word)
        }
    }

    /**
     * Converts the Api result object to the database entity
     * @param result The object to convert
     * @return The local persistent object entity
     */
    private fun transformResult(result: DictionaryApiResult): DictionaryWord {
        val wordBuilder = WordBuilder(
            result.headword.name,
            result.headword.source,
            result.headword.sourceId,
            result.headword.sortIndex
        )

        result.homographs.forEach {
            wordBuilder.attach(it.name, it.definitions)
        }

        return wordBuilder.build()
    }
}