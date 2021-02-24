package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.lexicon_memoria.database.entity.UserWordEntity.Columns

/**
 * The relation representing the words owned by a user
 * @param userId The foreign id for the user
 * @param headwordId The foreign id for the dictionary headword
 */
@Entity(
    tableName = UserWordEntity.name,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.Columns.id],
            childColumns = [Columns.userId]
        ),
        ForeignKey(
            entity = HeadwordEntity::class,
            parentColumns = [HeadwordEntity.Columns.id],
            childColumns = [Columns.headWordId]
        )
    ]
)
data class UserWordEntity(

    @ColumnInfo(name = Columns.userId, index = true)
    val userId: Long,

    @ColumnInfo(name = Columns.headWordId, index = true)
    val headwordId: Long,

    @ColumnInfo(name = Columns.dailyScore)
    val dailyScore: Int = 0,

    @ColumnInfo(name = Columns.dailyNextDate)
    val dailyNextDate: Int = 0,

    @ColumnInfo(name = Columns.weeklyScore)
    val weeklyScore: Int = 0,

    @ColumnInfo(name = Columns.weeklyNextDate)
    val weeklyNextDate: Int = 0,

    @ColumnInfo(name = Columns.monthlyScore)
    val monthlyScore: Int = 0,

    @ColumnInfo(name = Columns.monthlyNextDate)
    val monthlyNextDate: Int = 0,

    @ColumnInfo(name = Columns.creationDate)
    val creationDate: Long = System.currentTimeMillis()

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0

    companion object {
        const val name = "UserWord"
        const val foreignKey = "user_word_id"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val userId = UserEntity.foreignKey
            const val headWordId = HeadwordEntity.foreignKey
            const val dailyScore = "daily_score"
            const val dailyNextDate = "daily_next_date"
            const val weeklyScore = "weekly_score"
            const val weeklyNextDate = "weekly_next_date"
            const val monthlyScore = "monthly_score"
            const val monthlyNextDate = "monthly_next_date"
            const val creationDate = "creation_date"
        }
    }
}