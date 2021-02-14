package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.tableName)
data class UserEntity(
    val username: String,
    val email: String,
    @ColumnInfo(name = "creation_date") val creationDate: Long = System.currentTimeMillis()
) : BaseEntity() {
    @PrimaryKey(autoGenerate = true) override var id: Long = 0

    companion object {
        const val tableName = "users"
    }
}
