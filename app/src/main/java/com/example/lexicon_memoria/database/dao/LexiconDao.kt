package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.database.entity.LexiconEntity.Companion as Table

@Dao
abstract class LexiconDao(
    roomDatabase: RoomDatabase
) : BaseDao<LexiconEntity>(Table.tableName, roomDatabase)