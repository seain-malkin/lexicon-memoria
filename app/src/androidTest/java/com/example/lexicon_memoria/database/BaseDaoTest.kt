package com.example.lexicon_memoria.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.entity.HeadwordEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class BaseDaoTest {

    private lateinit var db: LexmemDatabase

    @Before
    fun setupDatabase() {
        LexmemDatabase.TEST_MODE = true
        db = LexmemDatabase.getDatabase(ApplicationProvider.getApplicationContext())
    }

    @Test
    @Throws(Exception::class)
    fun upsertSingleEntity() {
        runBlocking {
            val headword = HeadwordEntity("foobar", "testing")

            // First insert
            db.headWord().upsert(headword)

            // ID must a new row id
            assert(headword.id != -1L)

            // Second insert
            db.headWord().upsert(headword)

            val entity = db.headWord().get(headword.id)

            assert(entity != null)
        }
    }

    @Test
    @Throws(Exception::class)
    fun upsertListOfEntities() {
        runBlocking {
            val listOfEntities = mutableListOf(
                HeadwordEntity("foobar", "testing"),
                HeadwordEntity("barfoo", "resting")
            )

            // Insert list
            db.headWord().upsert(listOfEntities)

            var list = db.headWord().get()

            assert(list.size == listOfEntities.size)

            listOfEntities.forEach { assert(it.id != -1L) }

            // Add new entity, modify another, then upsert list
            listOfEntities.add(HeadwordEntity("rungrub", "testing"))
            listOfEntities[0].name = "starjump"

            db.headWord().upsert(listOfEntities)

            list = db.headWord().get()

            assert(list.size == listOfEntities.size)

            listOfEntities.forEach { assert(it.id != -1L) }

            // Ensure modified entity was updated in database
            val entity = db.headWord().get(listOfEntities[0].id)

            assert(entity != null)
            assert(entity?.name == "starjump")
        }
    }
}