package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.repository.LexiconRepository
import kotlinx.coroutines.launch

/**
 * View Model implementation for accessing lexicon objects
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconListViewModel(
    private val repository: LexiconRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** The username who owns the lexicons retrieved */
    val username: String = savedStateHandle["username"] ?:
        throw IllegalArgumentException("Missing username")

    /** Reference to an observed list of lexicon objects */
    val all: LiveData<List<LexiconEntity>> = repository.select(username).asLiveData()

    /**
     * Inserts a lexicon object asynchronously
     * @param[lexicon] The lexicon object to insert
     */
    fun insert(lexicon: LexiconEntity) = viewModelScope.launch {
        repository.insert(lexicon)
    }
}

/**
 * Factory for creating [LexiconListViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconListViewModelFactory(
    private val repository: LexiconRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (!modelClass.isAssignableFrom(LexiconListViewModel::class.java)) {
            throw IllegalArgumentException("Unkown ViewMdel class")
        }
        @Suppress("UNCHECKED_CAST")
        return LexiconListViewModel(repository, handle) as T
    }
}