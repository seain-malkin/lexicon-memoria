package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.Lexicon
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.database.entity.LexiconEntity.Companion as Table

@Dao
abstract class LexiconDao(
    roomDatabase: RoomDatabase
) : BaseDao<LexiconEntity>(Table.tableName, roomDatabase) {

    /**
     * Retrieves the Lexicon relation which contains a list of it's words
     * @param lexiconId The primary id key for the [LexiconEntity]
     */
    @Transaction
    @Query("SELECT * FROM ${Table.tableName} WHERE id = :lexiconId")
    abstract fun getWords(lexiconId: Long): Lexicon

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(lexicon: Lexicon): Long

    @Transaction
    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update(lexicon: Lexicon)

    @Transaction
    @Delete
    abstract fun delete(lexicon: Lexicon)
}