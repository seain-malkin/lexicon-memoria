package com.example.lexicon_memoria.database.dao

import androidx.room.*
import com.example.lexicon_memoria.database.entity.LexiconEntity

@Dao
interface LexiconDao {

    @Query("SELECT * FROM lexicons ORDER BY label")
    fun getLexicons() : List<LexiconEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg lexicons: LexiconEntity)

    @Update
    suspend fun update(vararg lexicons: LexiconEntity)

    @Delete
    suspend fun delete(vararg lexicons: LexiconEntity)

    @Query("DELETE FROM lexicons")
    suspend fun deleteAll()
}