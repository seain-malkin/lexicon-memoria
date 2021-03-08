package com.example.lexicon_memoria.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.UserWordEntity
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.database.entity.UserWordEntity.Companion as Table
import com.example.lexicon_memoria.database.entity.UserWordEntity.Columns

@Dao
abstract class UserWordDao(
    roomDatabase: RoomDatabase
) : BaseDao<UserWordEntity>(Table.name, roomDatabase) {

    @Query("SELECT COUNT(${Columns.id}) FROM ${Table.name} WHERE ${Columns.userId} = :userId")
    abstract suspend fun countWords(userId: Long): Int

    @Query("""
            SELECT * FROM ${Table.name} 
            WHERE ${Columns.userId} = :userId 
            AND ${Columns.dailyNextDate} < :time
            """)
    abstract fun getDaily(
        userId: Long,
        time: Long = System.currentTimeMillis()
    ): LiveData<List<WordWithScore>>
}