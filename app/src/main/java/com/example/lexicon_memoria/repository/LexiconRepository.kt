package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.LexiconDao
import com.example.lexicon_memoria.database.entity.LexiconEntity
import kotlinx.coroutines.flow.Flow

/**
 * A repository for accessing lexicons from an abstract persistent storage.
 * @author Seain Malkin (dev@seain.me)
 * @property[lexiconDao] The DAO for accessing lexicons
 */
class LexiconRepository(private val lexiconDao: LexiconDao) {

    /**
     * Select all lexicons for the given user
     * @param[username] The lexicon owner username
     * @return The list of lexicons
     */
    fun select(
        username: String
    ) : Flow<List<LexiconEntity>> {
        return lexiconDao.select(username)
    }

    /**
     * Select one lexicon given the user and lexicon names
     * @param[username] The owners name
     * @param[label] The label of the lexicon
     * @return A list of lexicons (Only 1 result)
     */
    fun select(
        username: String,
        label: String
    ) : Flow<List<LexiconEntity>> {
        return lexiconDao.select(username, label)
    }

    /**
     * Inserts a lexicon into persistant storage
     * @param[lexicon] The lexicon object to insert
     */
    @WorkerThread
    suspend fun insert(lexicon: LexiconEntity) {
        lexiconDao.insert(lexicon)
    }
}