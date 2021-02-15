package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import com.example.lexicon_memoria.database.entity.HeadwordEntity.Companion as Table

@Dao
abstract class HeadWordDao(
    roomDatabase: RoomDatabase
) : BaseDao<HeadwordEntity>(Table.tableName, roomDatabase) {

    @Query("SELECT * FROM ${Table.tableName} WHERE name = :key")
    abstract fun get(key: String): List<HeadwordEntity>
}