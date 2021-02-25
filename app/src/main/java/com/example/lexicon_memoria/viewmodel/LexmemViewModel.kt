package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.repository.UserWordRepository
import kotlinx.coroutines.launch

class LexmemViewModel(
    private val userWordRepo: UserWordRepository
) : ViewModel() {

    var userId: Long = 0L

    /**
     * Assigns a word to the user lexicon
     * @param word The word to add
     */
    fun onAddWord(userId: Long, word: DictionaryWord) {
        viewModelScope.launch {
            userWordRepo.addWord(userId, word)
        }
    }
}

class LexmemViewModelFactory(
    private val repository: UserWordRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
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