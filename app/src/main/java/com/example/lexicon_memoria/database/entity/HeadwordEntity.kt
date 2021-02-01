package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a dictionary word
 * @property name The word name
 * @property source Where the definition was sourced
 * @property sourceId The unique ID for the word at the source
 * @property sortIndex The order to display in relation to all words
 */
@Entity(tableName = HeadwordEntity.tableName)
data class HeadwordEntity(
    val name: String,
    val source: String,
    @ColumnInfo(name = "source_id") val sourceId: String? = null,
    @ColumnInfo(name = "sort_index") val sortIndex: String? = null
) : BaseEntity() {

    @PrimaryKey(autoGenerate = true) override var id: Long = 0
    @ColumnInfo(name = "modified_date") var modifiedDate: Long = System.currentTimeMillis()

    companion object {
        const val tableName = "HeadWord"
    }
}