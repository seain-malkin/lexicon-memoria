package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.UserDao
import com.example.lexicon_memoria.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class UserRepository(
        private val userDao: UserDao
) {

    /**
     * Finds all uses. Should only be one on device.
     * @return List of user entities as flow
     */
    fun get(): Flow<List<UserEntity>> {
        return userDao.get()
    }

    fun get(username: String): Flow<UserEntity?> {
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