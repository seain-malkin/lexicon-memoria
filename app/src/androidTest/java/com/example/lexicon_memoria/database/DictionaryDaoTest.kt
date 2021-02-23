package com.example.lexicon_memoria.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.builder.WordBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class DictionaryDaoTest {

    private lateinit var db: LexmemDatabase

    @Before
    fun setupDatabase() {
        LexmemDatabase.TEST_MODE = true
        db = LexmemDatabase.getDatabase(ApplicationProvider.getApplicationContext())
    }

    @Test
    @Throws(Exception::class)
    fun findExistingEntity() {
        runBlocking {
            val wordName = "Testword_existing"
            val word = buildWord(wordName)
            db.dictionary().save(word)

            // Search for non existing word
            var entity = db.dictionary().find("gidjhty")
            assert(entity == null)

            // Search for existing word
            entity = db.dictionary().find(wordName)
            assert(entity != null && entity == word)
        }
    }

    @Test
    @Throws(Exception::class)
    fun saveNewEntity() {
        runBlocking {
            val word = buildWord("Testword1")
            db.dictionary().save(word)

            // Check ID set for each element of the word object
            assert(word.headword.id != -1L)
            word.functions.forEach {
                assert(it.function.headwordId == word.headword.id)
                it.definitions.forEach { def ->
                    assert(def.wordFunctionId == it.function.id)
                    assert(def.id != -1L)
                }
            }
        }
    }

    @Test
    fun saveExistingEntity() {
        runBlocking {
            val wordName = "Testword2"
            val wordNameMod = wordName + "_mod"
            val word = buildWord(wordName)
            db.dictionary().save(word)

            word.headword.name = wordNameMod
            db.dictionary().save(word)

            val entity = db.dictionary().find(wordNameMod)
            assert(entity != null && entity == word)
        }
    }

    private fun buildWord(wordName: String): DictionaryWord {
        val wordBuilder = WordBuilder(wordName, "testclass")
        wordBuilder.attach("noun", listOf("string1", "string2", "string3"))
        wordBuilder.attach("verb", listOf("verbstring1", "verbstring2"))
        return wordBuilder.build()
    }
}