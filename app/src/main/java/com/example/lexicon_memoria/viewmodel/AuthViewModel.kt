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
 * @property userRepo
 * @param savedStateHandle
 */
class AuthViewModel(
    private val userRepo: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var user = MutableLiveData<UserEntity?>()

    init {
        initUser()
    }

    private fun initUser() {
        viewModelScope.launch {
            val defaultUser = userRepo.get(DEFAULT_USERNAME)
            if (defaultUser == null) {
                userRepo.insert(UserEntity(DEFAULT_USERNAME))
                user.value = userRepo.get(DEFAULT_USERNAME)
            } else {
                user.value = defaultUser
            }
        }
    }

    companion object {
        const val DEFAULT_USERNAME = "default_user"
    }
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