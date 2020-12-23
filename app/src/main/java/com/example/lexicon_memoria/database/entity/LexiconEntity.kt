package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "lexicons", primaryKeys = ["created_by", "label"])
data class LexiconEntity(

    @ColumnInfo(name = "created_by") // username
    val createdBy: String,

    @ColumnInfo(name = "label")
    val label: String,

    @ColumnInfo(name = "creation_timestamp")
    val creationTimestamp: Int
)