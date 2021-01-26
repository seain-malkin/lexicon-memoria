package com.example.lexicon_memoria.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpeechFunctionEntity(
        /** The word that this speech function belongs to */
        val word: String,
        /** Speech functional label. e.g Noun, verb, adjective */
        val label: String,
        /** A string relative to other words to use when sorting */
        val sort: String? = null,
        /** The period of time this word stems from */
        val epoch: String? = null
) {
    /** A unique id to represent this speech function */
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}