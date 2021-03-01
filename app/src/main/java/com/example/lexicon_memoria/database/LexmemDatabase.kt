package com.example.lexicon_memoria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.dao.*
import com.example.lexicon_memoria.database.entity.*

/**
 * Database class for the entire application
 * @author Seain Malkin (dev@seain.me)
 */
@Database(
    entities = [UserEntity::class,
                UserWordEntity::class,
                HeadwordEntity::class,
                DefinitionEntity::class,
                WordFunctionEntity::class,
                PronunciationEntity::class],
    version = 6,
    exportSchema = false
)
abstract class LexmemDatabase : RoomDatabase() {

    abstract fun definition(): DefinitionDao
    abstract fun headWord(): HeadWordDao
    abstract fun pronunciation(): PronunciationDao
    abstract fun userWord(): UserWordDao
    abstract fun wordFunction(): WordFunctionDao
    abstract fun user(): UserDao
    abstract fun dictionary(): DictionaryDao

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