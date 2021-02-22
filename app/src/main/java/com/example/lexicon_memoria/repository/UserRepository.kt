package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.UserDao
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
        return withContext(Dispatchers.IO) {
            userDao.get(username)
        }
    }

    /**
     * Gets the user and their owned words
     * @param userId The user id
     * @return The [Lexicon] object as a flow
     */
    fun getWords(userId: Long): Flow<Lexicon?> {
        return flow {
            emit(userDao.getWords(userId))
        }
    }

    /**
     * Inserts a user into persistent storage
     * @param[user] The user object to insert
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.upsert(user)
        }
    }
}