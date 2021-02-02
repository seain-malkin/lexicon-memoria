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
    tableName = WordFunctionEntity.tableName,
    foreignKeys = [
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = ["id"],
            childColumns = ["headword_id"]
        )
    ]
)
data class WordFunctionEntity(
    @ColumnInfo(name = "headword_id") var headwordId: Long,
    var name: String
) : BaseEntity() {

    @PrimaryKey(autoGenerate = true) override var id: Long = 0

    companion object {
        const val tableName = "WordFunction"
    }
}
