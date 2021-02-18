package com.example.lexicon_memoria.repository

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.LexmemDatabase
import com.example.lexicon_memoria.database.entity.builder.WordBuilder
import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource
import com.example.lexicon_memoria.dictionary.merriam_webster.CollegiateApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DictionaryRepositoryTest {

    private lateinit var db: LexmemDatabase
    private lateinit var remote: DictionaryRemoteDataSource
    private lateinit var repository: DictionaryRepository

    @Before
    fun setup() {
        LexmemDatabase.TEST_MODE = true
        db = LexmemDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        remote = DictionaryRemoteDataSource(CollegiateApi.source)
        repository = DictionaryRepository(db.dictionary(), remote)
    }

    @Test
    fun getExistingPersistentWord() {
        val uniqueWord = "euqinuunique"
        // Create unique word
        val builder = WordBuilder(uniqueWord, "test").apply {
            attach("nount", listOf("def1", "def2"))
        }
        val builtWord = builder.build()
        // Insert into local persistent storage
        db.dictionary().save(builtWord)

        // Search for word from dictionary repo
        runBlocking {
            repository.get(uniqueWord).collect {
                assert(it == builtWord)
            }
        }
    }
}