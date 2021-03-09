package com.example.lexicon_memoria.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lexicon_memoria.database.entity.UserEntity
import com.example.lexicon_memoria.repository.UserRepository
import kotlinx.coroutines.*
import kotlin.jvm.Throws

/**
 * [ViewModel] associated with AuthActivity
 * @property userRepo
 * @param savedStateHandle
 */
class UserViewModel(
    private val userRepo: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var user = MutableLiveData<UserEntity?>()

    init {
        // Load the local user or create a new one
        viewModelScope.launch {
            try {
                loadUser(savedStateHandle.get(ARG_USER_ID) ?: 0L)
            } catch (e: IllegalStateException) {
                createDefaultUser()
            }
        }
    }

    /**
     * Sets the user property value based on the user id given
     * Throws an exception if the user id doesn't exist
     * @param userId The user id to load
     */
    @Throws(IllegalStateException::class)
    private suspend fun loadUser(userId: Long) {
        // If user id set, try and load it. Otherwise, try select any user
        val localUser = if (userId > 0) userRepo.get(userId) else userRepo.getLocal()

        // If not found throw exception, otherwise set the user value
        when (localUser == null) {
            true -> throw IllegalStateException("The local user id doesn't exist.")
            false -> user.value = localUser
        }
    }

    /**
     * Creates a default local user and saves to database. The user value is then updated.
     * Throws an exception if the user still doesn't exist. (Shouldn't happen)
     */
    @Throws(IllegalStateException::class)
    private suspend fun createDefaultUser() {
        // Create and insert the new user
        userRepo.insert(UserEntity(DEFAULT_USERNAME))

        // Retrieve the new user and set the user property value
        val localUser = userRepo.getLocal()

        when(localUser == null) {
            true -> throw IllegalStateException("Unable to create a default user.")
            false -> user.value = localUser
        }
    }

    companion object {
        const val DEFAULT_USERNAME = "default_user"
        const val ARG_USER_ID = "auth_view_model:user_id"
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
        if (!modelClass.isAssignableFrom(UserViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        @Suppress("UNCHECKED_CAST")
        return UserViewModel(userRepository, handle) as T
    }
}