package com.example.lexicon_memoria.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.Lexicon
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.database.entity.UserEntity.Companion as Table

@Dao
abstract class UserDao(
    roomDatabase: RoomDatabase
) : BaseDao<UserEntity>(Table.tableName, roomDatabase) {

    /**
     * Returns the user object for the given username
     * @param username The username to retrieve
     * @return The user object or null
     */
    @Query("SELECT * FROM ${Table.tableName} WHERE username = :username")
    abstract suspend fun get(username: String): UserEntity?

    /**
     * Returns the user entity and a list of words they own
     * @param userId The user id who owns the words
     * @return A [Lexicon] object
     */
    @Transaction
    @Query("SELECT * FROM ${Table.tableName} WHERE id = :userId")
    abstract fun getWords(userId: Long): LiveData<List<Lexicon>>
}