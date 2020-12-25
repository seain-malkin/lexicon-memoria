package com.example.lexicon_memoria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.example.lexicon_memoria.repository.DictionaryRepository

class DictionarySearchViewModel(
    private val repository: DictionaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var searchKey: String = ""
    private val language = "en"

    var result: LiveData<List<DictionaryWord>> = search()

    fun update(key: String) {
        searchKey = key
        result = search()
    }

    private fun search() : LiveData<List<DictionaryWord>> {
        return repository.lookup(searchKey, language).asLiveData()
    }
}