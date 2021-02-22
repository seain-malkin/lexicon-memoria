package com.example.lexicon_memoria

import android.app.Application
import com.example.lexicon_memoria.database.LexmemDatabase
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.repository.DictionaryRepository
import com.example.lexicon_memoria.repository.UserRepository
import com.example.lexicon_memoria.dictionary.merriam_webster.CollegiateApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Provides access to application wide dependencies that are created as and when needed
 * @author Seain Malkin (dev@seain.me)
 */
class LexmemApplication : Application() {
    /** Scope for running application coroutines */
    val applicationScope = CoroutineScope(SupervisorJob())

    /** Reference to application database */
    val database by lazy { LexmemDatabase.getDatabase(this.applicationContext) }

    /** Reference to the dictionary repository */
    val dictionary by lazy {
        DictionaryRepository(
            database.dictionary(),
            DictionaryRemoteDataSource(CollegiateApi.source)
        )
    }

    /** Reference to the users repository */
    val users by lazy { UserRepository(database.user()) }
}