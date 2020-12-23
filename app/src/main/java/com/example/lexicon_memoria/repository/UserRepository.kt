package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.UserDao
import com.example.lexicon_memoria.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(
        private val userDao: UserDao
) {

    fun get(username: String) : Flow<List<UserEntity>> {
        return userDao.get(username)
    }

    /**
     * Inserts a user into persistent storage
     * @param[user] The user object to insert
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserEntity) {
        userDao.insert(user)
    }
}