package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a speech function (noun, verb, adjective) of a headword
 * @param headwordId The foreing id of the headword
 * @param name The speech function name
 */
@Entity(
    tableName = WordFunctionEntity.name,
    foreignKeys = [
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = [HeadwordEntity.Columns.id],
            childColumns = [WordFunctionEntity.Columns.headWordId]
        )
    ]
)
data class WordFunctionEntity(

    @ColumnInfo(name = Columns.headWordId, index = true)
    var headwordId: Long,

    @ColumnInfo(name = Columns.name)
    var label: String

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0

    companion object {
        const val name = "WordFunction"
        const val foreignKey = "word_function_id"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val headWordId = HeadwordEntity.foreignKey
            const val name = "name"
        }
    }
}
