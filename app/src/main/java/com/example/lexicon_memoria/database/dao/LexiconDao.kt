package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.Lexicon
import com.example.lexicon_memoria.database.entity.LexiconEntity
import kotlinx.coroutines.flow.Flow
import com.example.lexicon_memoria.database.entity.LexiconEntity.Companion as Table

@Dao
abstract class LexiconDao(
    roomDatabase: RoomDatabase
) : BaseDao<LexiconEntity>(Table.tableName, roomDatabase) {

    /**
     * Retrieves a list of lexicon entities owned by the given user
     * @param userId The database id of the user
     * @return A list of entities
     */
    @Query("SELECT * FROM ${Table.tableName} WHERE user_id = :userId")
    abstract fun getByUser(userId: Long): Flow<List<LexiconEntity>>

    /**
     * Retrieves the Lexicon relation which contains a list of its words
     * @param lexiconId The primary id key for the [LexiconEntity]
     */
    @Transaction
    @Query("SELECT * FROM ${Table.tableName} WHERE id = :lexiconId")
    abstract fun getWords(lexiconId: Long): Flow<Lexicon>
}