package com.example.lexicon_memoria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lexicon_memoria.database.dao.DictionaryDao
import com.example.lexicon_memoria.database.dao.LexiconDao
import com.example.lexicon_memoria.database.dao.UserDao
import com.example.lexicon_memoria.database.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Database class for the entire application
 * @author Seain Malkin (dev@seain.me)
 */
@Database(
    entities = [LexiconEntity::class,
                UserEntity::class,
                LexiconWordEntity::class,
                HeadwordEntity::class,
                DefinitionEntity::class,
                WordFunctionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LexmemDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun lexiconDao(): LexiconDao

    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        /** Name of database to use on device */
        private const val DATABASE_NAME = "lexmem_db"

        /** Object reference for singleton implementation */
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
                        .fallbackToDestructiveMigration()
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

            var lexicon = LexiconEntity(1, "General", System.currentTimeMillis())
            lexiconDao.insert(lexicon)

            lexicon = LexiconEntity(1, "Computer Science", System.currentTimeMillis())
            lexiconDao.insert(lexicon)
        }
    }
}