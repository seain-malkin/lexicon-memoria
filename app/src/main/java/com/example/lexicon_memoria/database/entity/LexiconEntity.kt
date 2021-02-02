package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Table representing a lexicon belonging to a user
 * @property userId The foreign user id of the creator
 * @property name The name of the lexicon
 * @property creationDate The timestamp the lexicon was created
 */
@Entity(
    tableName = LexiconEntity.tableName,
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LexiconEntity(
        @ColumnInfo(name="user_id") val userId: Long,
        val name: String,
        @ColumnInfo(name = "creation_date") val creationDate: Long
) : BaseEntity() {
    @PrimaryKey(autoGenerate = true) override var id: Long = 0

    companion object {
        const val tableName = "lexicons"
    }
}