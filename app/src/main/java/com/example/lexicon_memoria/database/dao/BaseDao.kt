package com.example.lexicon_memoria.database.dao

import android.annotation.SuppressLint
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.lexicon_memoria.database.entity.BaseEntity
import kotlinx.coroutines.flow.Flow
import java.lang.StringBuilder

/**
 * Data Access Object base implementing basic access methods.
 * Influenced by: https://medium.com/@berryhuang/android-room-generic-dao-27cfc21a4912
 * Implementation database table must have an 'id' column of type 'Long'.
 * @param tableName The name of the database table
 * @param roomDatabase The [Room] database object
 */
abstract class BaseDao<E : BaseEntity>(
    private val tableName: String,
    private val roomDatabase: RoomDatabase
) {

    @RawQuery
    abstract fun getList(q: SupportSQLiteQuery): List<E>

    @RawQuery
    abstract fun get(q: SupportSQLiteQuery): E?

    /**
     * Returns the entire table
     * @return A list of entity rows
     */
    fun get(): List<E> {
        return getList(SimpleSQLiteQuery("SELECT * FROM $tableName"))
    }

    /**
     * Select one row from table
     * @param id The row id
     * @return The table row entity
     */
    fun get(id: Long): E? {
        return get(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = $id"))
    }

    /**
     * Inserts or updates the table row. Returns the row id of the insert. On update, -1
     * @param e Object to insert
     * @return id or -1 on update
     */
    @Transaction
    open fun upsert(e: E) {
        val rowId = insert(e)
        if (rowId == -1L) {
            update(e)
        } else {
            e.id = rowId
        }
    }

    /**
     * Inserts or updates a list of rows.
     * @param e List of objects
     * @return List of ids or -1 on update
     */
    @Transaction
    open fun upsert(e: List<E>): List<Long> {
        val rowIds = insert(e)
        // Filter to list of failed inserts and update
        val updateList = rowIds.mapIndexedNotNull { i, id -> if (id == -1L) e[i] else null }
        if (updateList.isNotEmpty()) {
            update(updateList)
        }

        return rowIds
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(e: E): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(e: List<E>): List<Long>

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