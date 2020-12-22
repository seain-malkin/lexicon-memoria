package com.example.lexicon_memoria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lexicon_memoria.database.dao.LexiconDao
import com.example.lexicon_memoria.database.dao.UserDao
import com.example.lexicon_memoria.database.entity.LexiconEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.system.measureTimeMillis

/**
 * Database class for the entire application
 * @author Seain Malkin (dev@seain.me)
 */
@Database(entities = arrayOf(LexiconEntity::class), version = 1, exportSchema = false)
public abstract class LexmemDatabase : RoomDatabase() {

    abstract fun lexiconDao(): LexiconDao

    abstract fun userDao(): UserDao

    companion object {
        /** Name of database to use on device */
        private const val DATABASE_NAME = "lexmem_database"

        /** Object reference for singleton implemention */
        @Volatile
        private var INSTANCE: LexmemDatabase? = null

        /**
         * Initialises the class if required then returns it's object reference
         * @param[context] The calling context
         * @param[scope] The scope to launch coroutines in
         * @return Object reference of the database
         */
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LexmemDatabase {
            // Return instance or create instance when null
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LexmemDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(LexmemDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    /**
     * Injects test data into the database
     * @property[scope] The scope to run the coroutine in
     */
    private class LexmemDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        /** @see[onCreate] */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.lexiconDao())
                }
            }
        }

        /**
         * Creates test data and inserts into the database
         * @param[lexiconDao] The lexicon dao
         */
        suspend fun populateDatabase(lexiconDao: LexiconDao) {
            lexiconDao.deleteAll()

            var lexicon = LexiconEntity("sjam", "General", System.currentTimeMillis().toInt())
            lexiconDao.insert(lexicon)

            lexicon = LexiconEntity("sjam", "Computer Science", System.currentTimeMillis().toInt())
            lexiconDao.insert(lexicon)
        }
    }
}