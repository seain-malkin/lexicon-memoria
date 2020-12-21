package com.example.lexicon_memoria.viewmodel

import androidx.lifecycle.*
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.repository.LexiconRepository
import kotlinx.coroutines.launch

/**
 * View Model implementation for accessing lexicon objects
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconViewModel(
    private val repository: LexiconRepository
) : ViewModel() {

    /** Reference to an observed list of lexicon objects */
    val all: LiveData<List<LexiconEntity>> = repository.all.asLiveData()

    /**
     * Inserts a lexicon object asynchronously
     * @param[lexicon] The lexicon object to insert
     */
    fun insert(lexicon: LexiconEntity) = viewModelScope.launch {
        repository.insert(lexicon)
    }
}

/**
 * Factory for creating [LexiconViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconViewModelFactory(
    private val repository: LexiconRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(LexiconViewModel::class.java)) {
            throw IllegalArgumentException("Unkown ViewMdel class")
        }
        @Suppress("UNCHECKED_CAST")
        return LexiconViewModel(repository) as T
    }
}