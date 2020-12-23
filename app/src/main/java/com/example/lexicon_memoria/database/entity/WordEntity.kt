package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "words", primaryKeys = ["created_by", "lexicon_label", "label"])
data class WordEntity(

    @ColumnInfo(name = "created_by")
    val createdBy: String,

    @ColumnInfo(name = "lexicon_label")
    val lexiconLabel: String,

    @ColumnInfo(name = "label")
    val label: String,

    @ColumnInfo(name = "daily_score")
    val dailyScore: Int,

    @ColumnInfo(name = "daily_next_ts")
    val dailyNextTs: Int,

    @ColumnInfo(name = "weekly_score")
    val weeklyScore: Int,

    @ColumnInfo(name = "weekly_next_ts")
    val weeklyNextTs: Int,

    @ColumnInfo(name = "monthly_score")
    val monthlyScore: Int,

    @ColumnInfo(name = "monthly_next_ts")
    val monthlyNextTs: Int,

    @ColumnInfo(name = "creation_ts")
    val creationTs: Int
)
