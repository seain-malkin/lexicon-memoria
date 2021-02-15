package com.example.lexicon_memoria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lexicon_memoria.database.dao.*
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

    abstract fun definitionDao(): DefinitionDao
    abstract fun headWordDao(): HeadWordDao
    abstract fun lexiconWordDao(): LexiconWordDao
    abstract fun wordFunctionDao(): WordFunctionDao
    abstract fun userDao(): UserDao
    abstract fun lexiconDao(): LexiconDao
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        /** Set to true to use in memory database for testing */
        var TEST_MODE = false

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
            context: Context
        ): LexmemDatabase {
            // Return instance or create instance when null
            return INSTANCE ?: synchronized(this) {
                INSTANCE = when(TEST_MODE) {
                    false -> Room.databaseBuilder(
                        context.applicationContext,
                        LexmemDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    true -> Room.inMemoryDatabaseBuilder(
                        context.applicationContext,
                        LexmemDatabase::class.java
                    ).allowMainThreadQueries().build()
                }

                return INSTANCE!!
            }
        }

        fun close() {
            INSTANCE?.close()
        }
    }
}