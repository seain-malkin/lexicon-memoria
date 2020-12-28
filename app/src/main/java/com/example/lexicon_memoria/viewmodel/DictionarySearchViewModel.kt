package com.example.lexicon_memoria.viewmodel

import androidx.lifecycle.*
import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.example.lexicon_memoria.repository.DictionaryRepository
import kotlinx.coroutines.launch

class DictionarySearchViewModel(
    private val repository: DictionaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val language = "en"

    val result: MutableLiveData<DictionaryWord> by lazy {
        MutableLiveData<DictionaryWord>()
    }

    fun setWord(word: String) = viewModelScope.launch {
        result.value = repository.getWord(word, language)
    }
}