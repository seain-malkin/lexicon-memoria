package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DefinitionEntity(
    /** The id of the speech function the definition belongs to */
    @ColumnInfo(name = "function_id") val functionId: Int,
    /** The definition of the function */
    val definition: String,
    /** The order in which to display the definition */
    val order: Int = 0
) {
    /** A unique id identifying the definition */
    @PrimaryKey var id: Long = 0
}
