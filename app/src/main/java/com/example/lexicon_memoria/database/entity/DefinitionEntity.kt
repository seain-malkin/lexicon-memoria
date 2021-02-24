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
    tableName = DefinitionEntity.name,
    foreignKeys = [ForeignKey(
        entity = WordFunctionEntity::class,
        parentColumns = [WordFunctionEntity.Columns.id],
        childColumns = [DefinitionEntity.Columns.wordFunctionId]
    )]
)
data class DefinitionEntity(

    @ColumnInfo(name = Columns.wordFunctionId, index = true)
    var wordFunctionId: Long,

    @ColumnInfo(name = Columns.definition)
    var definition: String,

    @ColumnInfo(name = Columns.orderIndex)
    var orderIndex: Int = 0

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0

    companion object {
        const val name = "Definition"
        const val foreignKey = "definition_id"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val wordFunctionId = WordFunctionEntity.foreignKey
            const val definition = "definition"
            const val orderIndex = "order_index"
        }
    }
}