package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import com.example.lexicon_memoria.database.entity.HeadwordEntity.Companion as Table

@Dao
abstract class HeadWordDao(
    roomDatabase: RoomDatabase
) : BaseDao<HeadwordEntity>(Table.tableName, roomDatabase)