package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.example.lexicon_memoria.repository.DictionaryRepository
import com.example.lexicon_memoria.repository.LexiconRepository
import kotlinx.coroutines.launch

/**
 * View model for the Dictionary Search activity
 * @author Seain Malkin (dev@seain.me)
 * @param[repository] The repository for retrieving searched dictionary terms
 * @param[savedStateHandle] The callers saved state
 */
class DictionarySearchViewModel(
    private val repository: DictionaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** Language of the dictionary */
    private val language: String = savedStateHandle["language"] ?:
        throw IllegalArgumentException("Missing language property")

    /** The result of the dictionary lookup */
    val result: MutableLiveData<DictionaryWord> by lazy {
        MutableLiveData<DictionaryWord>()
    }

    /**
     * Requests the word from the dictionary and updates the result
     * @param[word] The word to lookup
     */
    fun lookup(word: String) = viewModelScope.launch {
        result.value = repository.getWord(word, language)
    }
}

/**
 * Factory for creating [DictionarySearchViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The dictionary repository
 * @property[owner] The lifecycle owner
 * @property[defaultArgs] Default arguments for the saved state
 */
class DictionarySearchViewModelFactory(
        private val repository: DictionaryRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        if (!modelClass.isAssignableFrom(DictionarySearchViewModel::class.java)) {
            throw IllegalArgumentException("Unkown ViewMdel class")
        }
        @Suppress("UNCHECKED_CAST")
        return DictionarySearchViewModel(repository, handle) as T
    }
}