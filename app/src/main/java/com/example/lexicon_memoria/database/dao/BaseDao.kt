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
    protected abstract suspend fun getList(q: SupportSQLiteQuery): List<E>

    @RawQuery
    protected abstract suspend fun get(q: SupportSQLiteQuery): E?

    /**
     * Returns the entire table
     * @return A list of entity rows
     */
    suspend fun get(): List<E> {
        return getList(SimpleSQLiteQuery("SELECT * FROM $tableName"))
    }

    /**
     * Select one row from table
     * @param id The row id
     * @return The table row entity
     */
    suspend fun get(id: Long): E? {
        return get(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = $id"))
    }

    /**
     * Inserts or updates the table row. The objects id is also updated.
     * @param e Object to insert
     */
    @Transaction
    open suspend fun upsert(e: E) {
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
    open suspend fun upsert(e: List<E>) {
        // Insert new rows. Apply row ids. Filter out inserted. Update remaining.
        insert(e).mapIndexedNotNull { i, id ->
            when (id == -1L) {
                true -> e[i]
                false -> { e[i].id = id; null }
            }
        }.let { if (it.isNotEmpty()) update(it) }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(e: E): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(e: List<E>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun update(e: E): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun update(e: List<E>)

    @Delete
    abstract suspend fun delete(e: E)

    @Delete
    abstract suspend fun delete(e: List<E>)

    @RawQuery
    abstract suspend fun deleteAll(q: SupportSQLiteQuery): Int

    suspend fun deleteAll() = deleteAll(SimpleSQLiteQuery("DELETE FROM $tableName"))
}