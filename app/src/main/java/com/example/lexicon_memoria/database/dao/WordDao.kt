package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.WordEntity
import com.example.lexicon_memoria.database.entity.WordWithFunctionalDefinitions
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM words WHERE created_by = :username AND lexicon_label = :lexiconLabel")
    fun select(username: String, lexiconLabel: String) : Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE created_by = :username " +
            "AND lexicon_label = :lexiconLabel AND label = :wordLabel")
    fun select(username: String, lexiconLabel: String, wordLabel: String) : Flow<List<WordEntity>>

    @Insert
    suspend fun insert(vararg words: WordEntity)

    @Update
    suspend fun update(vararg words: WordEntity)

    @Delete
    suspend fun delete(vararg words: WordEntity)

    @Query("DELETE FROM words")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM words WHERE label = :word")
    fun selectWithDefinitions(word: String) : Flow<List<WordWithFunctionalDefinitions>>
}