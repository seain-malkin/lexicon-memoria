package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the persistent local dictionary
 */
@Dao
abstract class DictionaryDao(val roomDatabase: RoomDatabase) {

    /**
     * Returns the complete dictionary word with all headwords and definitions
     * @param key The word to search for
     * @return A list of matches
     */
    @Transaction
    @Query("SELECT * FROM ${HeadwordEntity.tableName} WHERE name = :key")
    abstract fun find(key: String): Flow<DictionaryWord>


}