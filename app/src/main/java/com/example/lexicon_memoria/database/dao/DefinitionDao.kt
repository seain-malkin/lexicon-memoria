package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.DefinitionEntity
import com.example.lexicon_memoria.database.entity.DefinitionEntity.Companion as Table

@Dao
abstract class DefinitionDao(
    roomDatabase: RoomDatabase
) : BaseDao<DefinitionEntity>(Table.tableName, roomDatabase)