package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.repository.UserWordRepository
import kotlinx.coroutines.launch

class LexmemViewModel(
    private val userWordRepo: UserWordRepository
) : ViewModel() {

    /**
     * @property userId The database id of the current user
     *
     * A value of -1 represents a null value. If the property is accessed before it's set to a
     * valid id, an exception is thrown.
     */
    private val _userId = MutableLiveData(-1L)
    val userId: LiveData<Long>
        get() = _userId

    /**
     * @property totalWords The total number of words added by user
     */
    val totalWords: LiveData<Int> = Transformations.switchMap(_userId) { id ->
        userWordRepo.numWords(id)
    }

    val recentWords: LiveData<List<WordWithScore>> = Transformations.switchMap(totalWords) {
        userId.value?.let { id ->
            userWordRepo.recentWords(id, DEF_RECENT_LIMIT)
        } ?: throw IllegalStateException("User id is not set.")
    }

    /**
     * Setter for userId
     * @param id The user id of current user
     */
    fun setUserId(id: Long) {
        _userId.value = id
    }

    /**
     * Assigns a word to the user lexicon
     * @param word The word to add
     */
    fun addWord(word: DictionaryWord) {
        _userId.value?.let { uid ->
            viewModelScope.launch {
                userWordRepo.addWord(uid, word)
            }
        }
    }

    companion object {
        const val DEF_RECENT_LIMIT = 3
    }
}

class LexmemViewModelFactory(
    private val repository: UserWordRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (!modelClass.isAssignableFrom(LexmemViewModel::class.java)) {
            throw IllegalStateException("Unkown ViewModel class")
        }

        @Suppress("UNCHECKED_CAST")
        return LexmemViewModel(repository) as T
    }
}