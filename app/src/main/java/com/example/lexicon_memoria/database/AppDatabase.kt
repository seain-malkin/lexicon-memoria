package com.example.lexicon_memoria.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.dao.LexiconDao
import com.example.lexicon_memoria.database.entity.LexiconEntity

/**
 * Database class for the entire application
 * @author Seain Malkin (dev@seain.me)
 */
@Database(entities = arrayOf(LexiconEntity::class), version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun lexiconDao(): LexiconDao

    companion object {
        /** Object reference for singleton implemention */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Initialises the class if required then returns it's object reference
         * @param[context] The calling context
         * @return Object reference of the database
         */
        fun getDatabase(context: Context): AppDatabase {
            // Return instance or create instance when null
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}