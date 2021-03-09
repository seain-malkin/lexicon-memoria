package com.example.lexicon_memoria.repository

import android.util.Log
import com.example.lexicon_memoria.database.dao.UserWordDao
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.UserWordEntity

class UserWordRepository(
    private val userWordDao: UserWordDao
) {

    fun numWords(userId: Long) = userWordDao.countWords(userId)

    fun getDaily(userId: Long) = userWordDao.getDaily(userId)

    /**
     * Assigns a word to a user
     * @param userId The user id to assign to
     * @param word The word to add
     */
    suspend fun addWord(userId: Long, word: DictionaryWord) {
        userWordDao.upsert(UserWordEntity(userId, word.headword.id))
    }
}