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
@Entity(tableName = HeadwordEntity.name)
data class HeadwordEntity(

    @ColumnInfo(name = Columns.name)
    var label: String,

    @ColumnInfo(name = Columns.source)
    var source: String,

    @ColumnInfo(name = Columns.sourceId)
    var sourceId: String? = null,

    @ColumnInfo(name = Columns.sortIndex)
    var sortIndex: String? = null

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0

    @ColumnInfo(name = Columns.modifiedDate)
    var modifiedDate: Long = System.currentTimeMillis()

    companion object {
        const val name = "HeadWord"
        const val foreignKey = "head_word_id"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val name = "name"
            const val source = "source"
            const val sourceId = "source_id"
            const val sortIndex = "sort_index"
            const val modifiedDate = "modified_date"
        }
    }
}