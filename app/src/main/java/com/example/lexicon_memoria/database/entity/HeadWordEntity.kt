package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a dictionary word
 * @property
 */
@Entity(tableName = HeadWordEntity.NAME)
data class HeadWordEntity(
    @PrimaryKey val label: String,
    val source: String?,
    val sourceId: String?
) {
    var created: Long = System.currentTimeMillis()

    companion object {
        const val NAME = "headwords"
    }
}