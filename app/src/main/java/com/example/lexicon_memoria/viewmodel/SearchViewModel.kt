package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.repository.DictionaryRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.security.InvalidParameterException
import kotlin.jvm.Throws

class SearchViewModel(
    private val dict: DictionaryRepository
) : ViewModel() {

    @Throws(InvalidParameterException::class)
    fun search(query: String): LiveData<DictionaryWord?> {
        val result = MutableLiveData<DictionaryWord?>()
        viewModelScope.launch {
            dict.get(query)
                .catch { e ->
                    throw InvalidParameterException("Query not found in dictionary.")
                }
                .collect {
                result.value = it
            }
        }

        return result
    }
}

class SearchViewModelFactory(
    private val repository: DictionaryRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (!modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            throw IllegalStateException("Unkown ViewModel class")
        }

        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(repository) as T
    }
}