package com.example.lexicon_memoria.database.dao

import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.WordScoreEntity
import com.example.lexicon_memoria.database.entity.WordScoreEntity.Companion as Table

abstract class WordScoreDao(
    roomDatabase: RoomDatabase
) : BaseDao<WordScoreEntity>(Table.tableName, roomDatabase)