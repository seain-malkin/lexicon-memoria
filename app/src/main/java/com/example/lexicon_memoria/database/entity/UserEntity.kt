package com.example.lexicon_memoria.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

        @PrimaryKey @ColumnInfo(name = "username")
        val username: String,

        @ColumnInfo(name = "email")
        val email: String,

        @ColumnInfo(name = "creation_timestamp")
        val creationTimestamp: Int
)
