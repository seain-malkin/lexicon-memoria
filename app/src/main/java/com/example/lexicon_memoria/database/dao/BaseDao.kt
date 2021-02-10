package com.example.lexicon_memoria.database.dao

import android.annotation.SuppressLint
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import java.lang.StringBuilder

/**
 * Data Access Object base implementing basic access methods.
 * Influenced by: https://medium.com/@berryhuang/android-room-generic-dao-27cfc21a4912
 * Implementation database table must have an 'id' column of type 'Long'.
 * @param tableName The name of the database table
 * @param roomDatabase The [Room] database object
 */
abstract class BaseDao<E>(
    private val tableName: String,
    private val roomDatabase: RoomDatabase
) {

    @RawQuery
    abstract fun get(q: SupportSQLiteQuery): List<E>?

    /**
     * Select one row from table
     * @param id The row id
     * @return The table row entity
     */
    fun get(id: Long): E? {
        return get(listOf(id))?.firstOrNull()
    }

    /**
     * Select many rows for each id in the list
     * @param ids The list of ids to select
     * @return A list of row entities
     */
    fun get(ids: List<Long>): List<E>? {
        val idConcat = StringBuilder()
        ids.forEachIndexed { i, l ->
            if (i != 0) idConcat.append(",")
            idConcat.append("'").append("$l").append("'")
        }
        return get(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id IN ($idConcat);"))
    }

    fun getLive(id: Long): LiveData<E> {
        return MediatorLiveData<E>().apply {
            addSource(getLive(listOf(id))) {
                postValue(it?.firstOrNull())
            }
        }
    }


    @SuppressLint("RestrictedApi")
    fun getLive(ids: List<Long>): LiveData<List<E>> {
        return object : ComputableLiveData<List<E>>() {
            private var observer: InvalidationTracker.Observer? = null

            override fun compute(): List<E>? {
                if (observer == null) {
                    observer = object : InvalidationTracker.Observer(tableName) {
                        override fun onInvalidated(tables: Set<String>) = invalidate()
                    }
                    roomDatabase.invalidationTracker.addWeakObserver(observer)
                }
                return get(ids)
            }
        }.liveData
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(e: E): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insert(e: List<E>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update(e: E): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update(e: List<E>)

    @Delete
    abstract fun delete(e: E)

    @Delete
    abstract fun delete(e: List<E>)

    @RawQuery
    abstract fun deleteAll(q: SupportSQLiteQuery): Int

    fun deleteAll() = deleteAll(SimpleSQLiteQuery("DELETE FROM $tableName"))
}