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
     * Inserts a lexicon into persistant storage
     * @param[lexicon] The lexicon object to insert
     */
    @WorkerThread
    suspend fun insert(lexicon: LexiconEntity) {
        lexiconDao.insert(lexicon)
    }
}