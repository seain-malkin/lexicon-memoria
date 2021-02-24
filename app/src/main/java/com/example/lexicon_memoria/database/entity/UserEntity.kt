package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.name)
data class UserEntity(

    @ColumnInfo(name = Columns.username)
    val username: String,

    @ColumnInfo(name = Columns.email)
    val email: String? = null,

    @ColumnInfo(name = Columns.creationDate)
    val creationDate: Long = System.currentTimeMillis()

) : BaseEntity() {

    @ColumnInfo(name = Columns.id)
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0

    companion object {
        const val name = "users"
        const val foreignKey = "user_id"
    }

    class Columns {
        companion object {
            const val id = BaseEntity.id
            const val username = "username"
            const val email = "email"
            const val creationDate = "creation_date"
        }
    }
}
