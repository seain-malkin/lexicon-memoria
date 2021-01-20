package com.example.lexicon_memoria.database.entity

import androidx.room.PrimaryKey

data class SpeechFunctionEntity(
        /** The word that this speech function belongs to */
        val word: String,
        /** Speech functional label. e.g Noun, verb, adjective */
        val label: String,
        /** A string relative to other words to use when sorting */
        val sort: String,
        /** The period of time this word stems from */
        val epoch: String
) {
    /** A unique id to represent this speech function */
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}