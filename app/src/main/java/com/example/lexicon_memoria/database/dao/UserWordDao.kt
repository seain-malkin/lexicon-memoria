package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.UserWordEntity
import com.example.lexicon_memoria.database.entity.UserWordEntity.Companion as Table

@Dao
abstract class UserWordDao(
    roomDatabase: RoomDatabase
) : BaseDao<UserWordEntity>(Table.name, roomDatabase) {
}