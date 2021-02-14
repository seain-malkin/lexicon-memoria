package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

/**
 * [ViewModel] associated with AuthActivity
 * @property userRepository
 * @param savedStateHandle
 */
class AuthViewModel(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val users: LiveData<List<UserEntity>> = userRepository.get().asLiveData()
}

/**
 * @see AbstractSavedStateViewModelFactory
 */
class AuthViewModelFactory(
    private val userRepository: UserRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (!modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(userRepository, handle) as T
    }
}