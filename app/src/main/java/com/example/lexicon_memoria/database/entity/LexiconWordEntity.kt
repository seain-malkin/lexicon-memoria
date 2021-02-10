package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * The relation representing the words within a lexicon
 * @param lexiconId The foreign id for the lexicon
 * @param headwordId The foreign id for the dictionary headword
 */
@Entity(
    tableName = LexiconWordEntity.tableName,
    primaryKeys = ["lexicon_id", "headword_id"],
    foreignKeys = [
        ForeignKey(
            entity = LexiconEntity::class,
            parentColumns = ["id"],
            childColumns = ["lexicon_id"]
        ),
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = ["id"],
            childColumns = ["headword_id"]
        )
    ]
)
data class LexiconWordEntity(
    @ColumnInfo(name = "lexicon_id", index = true) val lexiconId: Long,
    @ColumnInfo(name = "headword_id", index = true) val headwordId: Long
) : BaseEntity() {

    override var id: Long = 0

    companion object {
        const val tableName = "LexiconWord"
    }
}