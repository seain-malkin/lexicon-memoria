package com.example.lexicon_memoria.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lexicon_memoria.database.entity.Lexicon
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.database.entity.UserEntity.Companion as Table
import com.example.lexicon_memoria.database.entity.UserEntity.Columns as Columns

@Dao
abstract class UserDao(
    roomDatabase: RoomDatabase
) : BaseDao<UserEntity>(Table.name, roomDatabase) {

    /**
     * Retrieves the local user id. Assumes only one entry in local table
     * @return The user object or null
     */
    @Query("SELECT * FROM ${Table.name} ORDER BY ${Columns.id} DESC LIMIT 1")
    abstract suspend fun getLocal(): UserEntity?

    /**
     * Returns the user object for the given username
     * @param username The username to retrieve
     * @return The user object or null
     */
    @Query("SELECT * FROM ${Table.name} WHERE username = :username")
    abstract suspend fun get(username: String): UserEntity?

    /**
     * Returns the user entity and a list of words they own
     * @param userId The user id who owns the words
     * @return A [Lexicon] object
     */
    @Transaction
    @Query("SELECT * FROM ${Table.name} WHERE id = :userId")
    abstract fun getWords(userId: Long): LiveData<List<Lexicon>>
}