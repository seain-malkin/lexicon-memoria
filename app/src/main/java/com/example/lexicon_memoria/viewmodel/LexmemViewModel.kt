package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.fragments.ModuleListFragment
import com.example.lexicon_memoria.module.AllWordsModule
import com.example.lexicon_memoria.module.BaseModule
import com.example.lexicon_memoria.repository.UserWordRepository
import kotlinx.coroutines.launch

class LexmemViewModel(
    private val userWordRepo: UserWordRepository
) : ViewModel() {

    var userId: Long = 0L

    private var _modules = MutableLiveData<List<BaseModule>>()
    val modules: LiveData<List<BaseModule>> get() = _modules

    init {
        _modules.value = listOf(AllWordsModule(), AllWordsModule())
    }

    fun getDaily() = userWordRepo.getDaily(userId)

    /**
     * Assigns a word to the user lexicon
     * @param word The word to add
     */
    fun addWord(word: DictionaryWord) {
        viewModelScope.launch {
            userWordRepo.addWord(userId, word)
        }
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