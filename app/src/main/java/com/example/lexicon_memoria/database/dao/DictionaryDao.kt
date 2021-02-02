package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the persistent local dictionary
 */
@Dao
interface DictionaryDao {

    /**
     * Returns the complete dictionary word with all headwords and definitions
     * @param key The word to search for
     * @return A list of matches
     */
    @Transaction
    @Query("SELECT * FROM ${HeadwordEntity.tableName} WHERE name LIKE :key")
    fun find(key: String): List<DictionaryWord>

    /**
     * Saves the word to database
     * @param word The word to save
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg word: DictionaryWord)

    /**
     * Updates the word in the database
     * @param word THe word to update
     */
    @Transaction
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(vararg word: DictionaryDao)

    /**
     * Deletes the word from the database
     * @param word The word to delete
     */
    @Transaction
    @Delete
    fun delete(vararg word: DictionaryWord)
}