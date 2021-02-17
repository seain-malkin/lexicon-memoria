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
class HeadWordDaoTest {

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
    fun writeHeadwordAndReadAsList() {
        val headword = HeadwordEntity("foobar", "test_class")
        db.headWord().insert(headword)

        val asList = db.headWord().get("foobar")

        assert(headword == asList[0])
    }

    @Test
    @Throws(Exception::class)
    fun selectNoEntityAndReadEmptyList() {
        val emptyList = db.headWord().get("nomatch")

        assert(emptyList.isEmpty())
    }
}