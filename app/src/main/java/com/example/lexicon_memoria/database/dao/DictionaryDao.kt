package com.example.lexicon_memoria.database.dao

import android.database.sqlite.SQLiteAbortException
import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.IGNORE
import com.example.lexicon_memoria.database.entity.*
import com.example.lexicon_memoria.database.entity.HeadwordEntity.Companion as Table

/**
 * Data Access Object for the persistent local dictionary
 */
@Dao
interface DictionaryDao {

    @Query("SELECT * FROM ${Table.NAME} WHERE label = :word LIMIT 1")
    suspend fun get(word: String) : List<WordWithFunctionalDefinitions>

    /**
     * Insert for [HeadwordEntity]. Exception triggered on failure.
     * @param entity The new entity to insert
     */
    @Insert(onConflict = ABORT)
    fun insert(entity: HeadwordEntity)

    /**
     * Update for [HeadwordEntity].
     * @param entity The entity to update
     */
    @Update(onConflict = IGNORE)
    fun update(entity: HeadwordEntity)

    /**
     * Insert for [SpeechFunctionEntity].
     * @param entity The entity to insert
     * @return The database row id
     */
    @Insert(onConflict = IGNORE)
    fun insert(entity: SpeechFunctionEntity) : Long

    /**
     * Update for [SpeechFunctionEntity].
     * @param entity The entity to update
     */
    @Update
    fun update(entity: SpeechFunctionEntity)

    /**
     * Insert for [DefinitionEntity].
     * @param entity The entity to insert
     * @return The database row id
     */
    @Insert(onConflict = ABORT)
    fun insert(entity: DefinitionEntity) : Long

    /**
     * Update for [DefinitionEntity]
     * @param entity The entity to update
     */
    @Update
    fun update(entity: DefinitionEntity)

    /**
     * Transaction to insert or update a [WordWithFunctionalDefinitions] entity.
     * @param entity The entity to put into the database
     */
    @Transaction
    fun upsert(entity: WordWithFunctionalDefinitions) {
        // Either insert or update HeadWordEntity
        try {
            insert(entity.headword)
        } catch (e: SQLiteAbortException) {
            update(entity.headword)
        }

        // Apply speech functions to database
        entity.functions.forEach { upsert(it) }
    }

    /**
     * Transaction to insert or update a [FunctionWithDefinitions] entity.
     * @param entity The entity to put into the database
     */
    @Transaction
    private fun upsert(entity: FunctionWithDefinitions) {
        // If id is already set then update, otherwise insert a new row
        when (entity.function.id) {
            0L -> {
                // Insert and retrieve new row id
                entity.function.id = insert(entity.function)

                // Apply row id to each definition
                entity.definitions.forEach { it.functionId = entity.function.id }
            }
            else -> update(entity.function)
        }

        // Apply definitions to database
        entity.definitions.forEach { upsert(it) }
    }

    /**
     * Transaction to insert or update a [DefinitionEntity].
     * @param entity The entity to put into the database
     */
    @Transaction
    private fun upsert(entity: DefinitionEntity) {
        // Attempt to insert and assign row id to entity. Otherwise update.
        when (entity.id) {
            0L -> entity.id = insert(entity)
            else -> update(entity)
        }
    }
}