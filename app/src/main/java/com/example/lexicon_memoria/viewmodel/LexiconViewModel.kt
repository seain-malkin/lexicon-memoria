package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.WordEntity
import com.example.lexicon_memoria.fragments.LexiconViewFragment
import com.example.lexicon_memoria.repository.DictionaryRepository
import kotlinx.coroutines.launch

class LexiconViewModel(
    private val repository: DictionaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    /** The username of the data owner */
    private val username: String = savedStateHandle[LexiconViewFragment.ARG_USERNAME] ?:
        throw IllegalArgumentException("Missing username")

    /** The lexicon label the word belongs to */
    private val lexiconLabel: String = savedStateHandle[LexiconViewFragment.ARG_LEXICON_LABEL] ?:
        throw IllegalArgumentException("Missing lexicon label")

    /** Retrieve all words in a given lexicon */
    //val all: LiveData<List<WordEntity>> = repository.select(username, lexiconLabel).asLiveData()

    /**
     * Inserts a word
     * @param[word] The word object to insert
     */
    fun insert(word: WordEntity) = viewModelScope.launch {
        //repository.insert(word)
        // TODO: Add word to lexicon
    }
}

/**
 * Factory for creating [LexiconViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The lexicon repository object
 */
class LexiconViewModelFactory(
        private val repository: DictionaryRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (!modelClass.isAssignableFrom(LexiconViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        @Suppress("UNCHECKED_CAST")
        return LexiconViewModel(repository, handle) as T
    }
}