package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.dictionary.Word
import com.example.lexicon_memoria.repository.DictionaryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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

    /** The result of the dictionary lookup */
    private val _lookupResult = MutableLiveData<Word>()
    val lookupResult: LiveData<Word> get() = _lookupResult

    /** Whether a search request is still waiting for a result */
    val searchInProgress: MutableLiveData<Boolean> = MutableLiveData(false)

    /** Whether user has changed the search key since last result */
    val searchKeyChanged: MutableLiveData<Boolean> = MutableLiveData(false)

    /**
     * Requests the word from the dictionary and updates the result
     * @param[word] The word to lookup
     */
    fun search(word: String) = viewModelScope.launch {
        repository.getWord(word)
                .onStart {
                    searchInProgress.value = true
                }
                .onCompletion { cause ->
                    searchInProgress.value = false
                    if (cause != null) {
                        // Update UI for error
                    }
                }
                .catch { e -> Log.i("Search Exception", "$e") }
                .collect { result -> _lookupResult.value = result }
    }
}

/**
 * Factory for creating [DictionarySearchViewModel]. Allows the view model to survive configuration
 * and lifecycle changes.
 * @author Seain Malkin (dev@seain.me)
 * @property[repository] The dictionary repository
 * @param[owner] The lifecycle owner
 * @param[defaultArgs] Default arguments for the saved state
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