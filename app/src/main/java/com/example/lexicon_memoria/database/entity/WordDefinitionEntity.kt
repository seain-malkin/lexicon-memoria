package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
        tableName = "${WordDefinitionEntity.NAME}",
        primaryKeys = [
            "${WordDefinitionEntity.COL_USERNAME}",
            "${WordDefinitionEntity.COL_WORD}",
            "${WordDefinitionEntity.COL_ORDER}"
        ]
)
data class WordDefinitionEntity(

    // The user name of the creator
    @ColumnInfo(name = "$COL_USERNAME")
    val createdBy: String,

    // The label of the wordEntity that owns this definition
    @ColumnInfo(name = "$COL_WORD")
    val wordLabel: String,

    // The order in which to display the definitions
    @ColumnInfo(name = "$COL_ORDER")
    val orderIndex: Int,

    // The definition text displayed to user
    @ColumnInfo(name = "$COL_TEXT")
    val text: String
) {
    companion object {
        const val NAME = "word_definitions"
        const val COL_USERNAME = "created_by"
        const val COL_WORD = "word_label"
        const val COL_ORDER = "order_index"
        const val COL_TEXT = "display_text"
    }
}