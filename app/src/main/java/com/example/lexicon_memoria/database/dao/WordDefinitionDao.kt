package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.WordDefinitionEntity
import com.example.lexicon_memoria.database.entity.WordDefinitionEntity.Companion as table
import kotlinx.coroutines.flow.Flow

interface WordDefinitionDao {

    @Query("""SELECT ${table.COL_TEXT}, ${table.COL_ORDER} 
                FROM ${table.NAME}
                WHERE ${table.COL_USERNAME} = :username
                AND ${table.COL_WORD} = :wordLabel
                ORDER BY ${table.COL_ORDER} ASC""")
    fun select(username: String, wordLabel: String) : Flow<List<WordDefinitionEntity>>

    @Insert
    suspend fun insert(vararg wordDefinitions: WordDefinitionEntity)

    @Update
    suspend fun update(vararg wordDefinitions: WordDefinitionEntity)

    @Delete
    suspend fun delete(vararg wordDefinitions: WordDefinitionEntity)

    @Query("DELETE FROM ${table.NAME}")
    suspend fun deleteAll()
}