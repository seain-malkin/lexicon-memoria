package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.LexiconWordEntity
import com.example.lexicon_memoria.database.entity.LexiconWordEntity.Companion as Table

@Dao
abstract class LexiconWordDao(
    roomDatabase: RoomDatabase
) : BaseDao<LexiconWordEntity>(Table.tableName, roomDatabase)