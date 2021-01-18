package com.example.lexicon_memoria.dictionary

/**
 * Represents a dictionary word as homographs
 */
data class Word(
    val head: String,
    val homographs: List<Homograph>
) {
    override fun toString(): String {
        return head
    }
}