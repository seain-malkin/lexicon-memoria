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

}