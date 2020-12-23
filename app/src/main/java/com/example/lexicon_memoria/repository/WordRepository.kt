package com.example.lexicon_memoria.repository

import androidx.annotation.WorkerThread
import com.example.lexicon_memoria.database.dao.WordDao
import com.example.lexicon_memoria.database.entity.WordEntity
import kotlinx.coroutines.flow.Flow

/**
 * A repository for accessing words belonging to a lexicon
 * @author Seain Malkin (dev@seain.me)
 * @property[wordDao] The DAO for accessing words
 */
class WordRepository(
    private val wordDao: WordDao
) {

    /**
     * Select all words of a given lexicon
     * @param[username] The owner name
     * @param[lexiconLabel] The lexicon name
     * @return  The list of words
     */
    fun select(
        username: String,
        lexiconLabel: String
    ) : Flow<List<WordEntity>> {
        return wordDao.select(username, lexiconLabel)
    }

    /**
     * Select a particular word of a given lexicon
     * @param[username] The owner name
     * @param[lexiconLabel] The lexicon name
     * @param[label] The word label
     * @return The word as a list (one result)
     */
    fun select(
        username: String,
        lexiconLabel: String,
        label: String
    ) : Flow<List<WordEntity>> {
        return wordDao.select(username, lexiconLabel, label)
    }

    /**
     * Inserts a word into persistent storage
     * @param[word] The word object to insert
     */
    @WorkerThread
    suspend fun insert(word: WordEntity) {
        wordDao.insert(word)
    }
}