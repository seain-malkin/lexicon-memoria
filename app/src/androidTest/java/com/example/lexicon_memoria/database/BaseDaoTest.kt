package com.example.lexicon_memoria.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.entity.HeadwordEntity
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

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertThenUpdateThenDelete() {
        // Insert
        val headword = HeadwordEntity("foobar", "testing")
        headword.id = db.headWord().insert(headword)

        var list = db.headWord().get("foobar")

        assert(list.isNotEmpty())
        assert(list[0] == headword)
        assert(list[0].id == headword.id)

        // Update should fail
        headword.name = "barfoo"

        db.headWord().insert(headword)

        list = db.headWord().get("barfoo")
        assert(list.isEmpty())

        // Update should succeed
        db.headWord().update(headword)
        list = db.headWord().get("barfoo")

        assert(list.isNotEmpty())
        assert(list[0] == headword)

        // Delete
        db.headWord().delete(headword)
        var entity = db.headWord().get(headword.id)

        assert(entity == null)
    }

    @Test
    @Throws(Exception::class)
    fun upsertSingleEntity() {
        val headword = HeadwordEntity("foobar", "testing")

        // First insert
        db.headWord().upsert(headword)

        // ID must a new row id
        assert(headword.id != -1L)

        // Second insert
        db.headWord().upsert(headword)

        // Check there is only one row
        val getList = db.headWord().get()

        assert(getList.size == 1)
    }

    @Test
    @Throws(Exception::class)
    fun upsertListOfEntities() {
        val listOfEntities = mutableListOf(
            HeadwordEntity("foobar","testing"),
            HeadwordEntity("barfoo", "resting")
        )

        // Insert list and assign id to each object
        var idList = db.headWord().upsert(listOfEntities)

        assert(idList.size == listOfEntities.size)
        idList.forEachIndexed { i, id -> listOfEntities[i].id = id }

        listOfEntities.forEach { assert(it.id != -1L) }

        // Add new entity, modify another, then upsert list
        listOfEntities.add(HeadwordEntity("rungrub", "testing"))
        listOfEntities[0].name = "starjump"

        idList = db.headWord().upsert(listOfEntities)

        assert(idList.size == listOfEntities.size)
        idList.forEachIndexed { i, id -> if (id != -1L) listOfEntities[i].id = id }

        listOfEntities.forEach { assert(it.id != -1L) }

        // Ensure modified entity was updated in database
        val entity = db.headWord().get(listOfEntities[0].id)

        assert(entity != null)
        assert(entity?.name == "starjump")
    }
}