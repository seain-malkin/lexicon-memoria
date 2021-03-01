package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents the pronunciation of a headword
 */
@Entity(
    tableName = PronunciationEntity.name,
    foreignKeys = [
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = [HeadwordEntity.Columns.id],
            childColumns = [PronunciationEntity.Columns.headwordId]
        )
    ]
)
data class PronunciationEntity(

    @ColumnInfo(name = Columns.spoken)
    var spoken: String,

    @ColumnInfo(name = Columns.audio)
    var audio: String? = null,

    @ColumnInfo(name = Columns.headwordId)
    var headwordId: Long = 0L

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0L

    companion object {
        const val name = "Pronunciation"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val headwordId = HeadwordEntity.foreignKey
            const val spoken = "spoken"
            const val audio = "audio"
        }
    }
}