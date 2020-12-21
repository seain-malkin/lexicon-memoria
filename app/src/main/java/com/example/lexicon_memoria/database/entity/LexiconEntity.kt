package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lexicons")
data class LexiconEntity(

    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "label")
    val label: String,

    @ColumnInfo(name = "creation_timestamp")
    val creation_timestamp: Int
)
