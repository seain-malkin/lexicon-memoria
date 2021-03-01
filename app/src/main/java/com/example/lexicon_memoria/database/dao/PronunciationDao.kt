package com.example.lexicon_memoria.database.dao

import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.lexicon_memoria.database.entity.PronunciationEntity

@Dao
abstract class PronunciationDao(
    roomDatabase: RoomDatabase
) : BaseDao<PronunciationEntity>(PronunciationEntity.name, roomDatabase)