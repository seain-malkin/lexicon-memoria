package com.example.lexicon_memoria.database.dao

import android.annotation.SuppressLint
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
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
    abstract fun getList(q: SupportSQLiteQuery): Flow<List<E>>

    @RawQuery
    abstract fun get(q: SupportSQLiteQuery): Flow<E>

    /**
     * Returns the entire table
     * @return A list of entity rows
     */
    fun get(): Flow<List<E>> {
        return getList(SimpleSQLiteQuery("SELECT * FROM $tableName"))
    }

    /**
     * Select one row from table
     * @param id The row id
     * @return The table row entity
     */
    fun get(id: Long): Flow<E> {
        return get(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = $id"))
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(e: E): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(e: List<E>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(e: E): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(e: List<E>)

    @Delete
    abstract suspend fun delete(e: E)

    @Delete
    abstract suspend fun delete(e: List<E>)

    @RawQuery
    abstract suspend fun deleteAll(q: SupportSQLiteQuery): Int

    suspend fun deleteAll() = deleteAll(SimpleSQLiteQuery("DELETE FROM $tableName"))
}