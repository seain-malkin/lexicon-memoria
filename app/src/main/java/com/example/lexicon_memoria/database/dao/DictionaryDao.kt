package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.LexmemDatabase
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import com.example.lexicon_memoria.database.entity.Homograph
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the persistent local dictionary
 */
@Dao
abstract class DictionaryDao(private val roomDatabase: LexmemDatabase) {

    /**
     * Returns the complete dictionary word with all headwords and definitions
     * @param key The word to search for
     * @return A list of matches
     */
    @Transaction
    @Query("SELECT * FROM ${HeadwordEntity.name} WHERE name = :key")
    abstract suspend fun find(key: String): DictionaryWord?

    /**
     * Inserts into or updates the database by breaking the object apart and invoking the DAO
     * methods for each component
     * @param word The object to insert
     */
    @Transaction
    open suspend fun save(word: DictionaryWord) {
        // Update the headword
        roomDatabase.headWord().upsert(word.headword)

        word.pronunciation?.let {
            // Get headwordId reference and save pronunciation
            it.headwordId = word.headword.id
            roomDatabase.pronunciation().upsert(it)
        }

        // Save each homograph and link the headword id
        word.functions.forEach { save(word.headword.id, it) }
    }

    /**
     * Breaks a Homograph object into its components and inserts into the database
     * @param headwordId The row id of the headword this homograph belongs to
     * @param homograph The object to insert
     */
    @Transaction
    protected open suspend fun save(headwordId: Long, homograph: Homograph) {
        homograph.function.headwordId = headwordId

        // Save the word function
        roomDatabase.wordFunction().upsert(homograph.function)

        // Update the word function id for each definition entity
        homograph.definitions.forEach { it.wordFunctionId = homograph.function.id }

        // Save the definitions
        roomDatabase.definition().upsert(homograph.definitions)
    }
}