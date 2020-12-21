package com.example.lexicon_memoria

import android.app.Application
import com.example.lexicon_memoria.database.LexmemDatabase
import com.example.lexicon_memoria.repository.LexiconRepository
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
    val database by lazy { LexmemDatabase.getDatabase(this, applicationScope) }

    /** Reference to the lexicon repository */
    val lexicons by lazy { LexiconRepository(database.lexiconDao()) }
}