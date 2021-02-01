package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * A definition belonging to a speech function
 * @property wordFunctionId The foreign id of the speech function
 * @property definition The displayable definition
 * @property orderIndex The order in which to display it in relation to other definitions
 */
@Entity(
    tableName = DefinitionEntity.tableName,
    foreignKeys = [ForeignKey(
        entity = WordFunctionEntity::class,
        parentColumns = ["id"],
        childColumns = ["word_function_id"]
    )]
)
data class DefinitionEntity(
    @ColumnInfo(name = "word_function_id") val wordFunctionId: Long,
    val definition: String,
    @ColumnInfo(name = "order_index") var orderIndex: Int = 0
) : BaseEntity() {

    @PrimaryKey(autoGenerate = true) override var id: Long = 0

    companion object {
        const val tableName = "Definition"
    }
}