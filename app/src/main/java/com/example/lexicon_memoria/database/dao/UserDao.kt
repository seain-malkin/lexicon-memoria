package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.database.entity.UserEntity.Companion as Table
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao(roomDatabase: RoomDatabase) : BaseDao<UserEntity>(Table.tableName, roomDatabase) {

    @Query("SELECT * FROM users WHERE username = :username")
    abstract fun get(username: String) : Flow<List<UserEntity>>
}