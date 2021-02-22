package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * The relation representing the words owned by a user
 * @param userId The foreign id for the user
 * @param headwordId The foreign id for the dictionary headword
 */
@Entity(
    tableName = UserWordEntity.tableName,
    primaryKeys = ["user_id", "headword_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = ["id"],
            childColumns = ["headword_id"]
        )
    ]
)
data class UserWordEntity(
    @ColumnInfo(name = "user_id", index = true) val userId: Long,
    @ColumnInfo(name = "headword_id", index = true) val headwordId: Long
) : BaseEntity() {

    override var id: Long = 0

    companion object {
        const val tableName = "UserWord"
    }
}