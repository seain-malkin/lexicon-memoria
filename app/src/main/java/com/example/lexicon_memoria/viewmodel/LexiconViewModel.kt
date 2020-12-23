package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.repository.LexiconRepository

class LexiconViewModel(
    private val repository: LexiconRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}

/**
 * Factory for creating [LexiconViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconViewModelFactory(
    private val repository: LexiconRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (!modelClass.isAssignableFrom(LexiconViewModel::class.java)) {
            throw IllegalArgumentException("Unkown ViewMdel class")
        }
        @Suppress("UNCHECKED_CAST")
        return LexiconViewModel(repository, handle) as T
    }
}