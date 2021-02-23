package com.example.lexicon_memoria.repository

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexicon_memoria.database.LexmemDatabase
import com.example.lexicon_memoria.database.entity.UserEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    private lateinit var db: LexmemDatabase
    private lateinit var repository: UserRepository

    @Before
    fun setupDatabase() {
        LexmemDatabase.TEST_MODE = true
        db = LexmemDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        repository = UserRepository(db.user())
    }

    @Test
    fun insertAndGetUser() {
        val username = "testuser"
        runBlocking {
            repository.insert(UserEntity(username, "user@domain.com"))
            val entity = repository.get(username)
            assert(entity != null && entity.username == username )
        }
    }
}