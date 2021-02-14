package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.WordFunctionEntity
import com.example.lexicon_memoria.database.entity.WordFunctionEntity.Companion as Table

@Dao
abstract class WordFunctionDao(
    roomDatabase: RoomDatabase
) : BaseDao<WordFunctionEntity>(Table.tableName, roomDatabase)