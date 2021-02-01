package com.example.lexicon_memoria.database.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.database.entity.UserEntity.Companion as Table
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao(
    roomDatabase: RoomDatabase
) : BaseDao<UserEntity>(Table.tableName, roomDatabase) {
    /**
     * Returns the user object for the given username
     * @param username The username to retrieve
     * @return The user object or null
     */
    fun get(username: String) : UserEntity? {
        return get(SimpleSQLiteQuery(
                "SELECT * FROM ${Table.tableName} WHERE username = $username"
        ))?.firstOrNull()
    }
}