package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.LexiconDao
import com.example.lexicon_memoria.database.entity.LexiconEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A repository for accessing lexicons from an abstract persistent storage.
 * @author Seain Malkin (dev@seain.me)
 * @property[lexiconDao] The DAO for accessing lexicons
 */
class LexiconRepository(private val lexiconDao: LexiconDao) {

    /**
     * Retrieves a list of lexicons created by the given user
     * @param The database id of the user
     * @return A flow consisting of a list of lexicon entities
     */
    suspend fun getUserLexicons(userId: Long): Flow<List<LexiconEntity>> {
        return flow { lexiconDao.getByUser(userId) }
    }

    /**
     * Inserts a lexicon into persistant storage
     * @param[lexicon] The lexicon object to insert
     */
    @WorkerThread
    suspend fun insert(lexicon: LexiconEntity) {
        lexiconDao.upsert(lexicon)
    }
}