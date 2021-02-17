package com.example.lexicon_memoria.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.entity.UserEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: LexmemDatabase

    @Before
    fun setupDatabase() {
        LexmemDatabase.TEST_MODE = true
        db = LexmemDatabase.getDatabase(ApplicationProvider.getApplicationContext())
    }

    @Test
    @Throws(Exception::class)
    fun insertAndSelectUser() {
        val username = "primeuser"

        // Select non-existent
        var entity = db.user().get(username)
        assert(entity == null)

        // Insert entity
        val user = UserEntity(username, "hello@example.com")
        db.user().upsert(user)

        // Select existing entity
        entity = db.user().get(username)
        assert(entity != null && entity.username == username)
    }
}