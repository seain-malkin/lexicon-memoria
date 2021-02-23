package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lexicon_memoria.database.dao.UserDao
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.Lexicon
import com.example.lexicon_memoria.database.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UserRepository(
        private val userDao: UserDao
) {

    /**
     * Gets local user
     * @param username The name of the user
     * @return The user entity
     */
    suspend fun get(username: String): UserEntity? {
        return userDao.get(username)
    }

    /**
     * Gets a list of all users words as live data
     * @param userId The user id
     * @return Live data with the word list
     */
    fun getWords(userId: Long): LiveData<List<Lexicon>> {
        return userDao.getWords(userId)
    }

    /**
     * Inserts a user into persistent storage
     * @param[user] The user object to insert
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserEntity) {
        userDao.upsert(user)
    }
}