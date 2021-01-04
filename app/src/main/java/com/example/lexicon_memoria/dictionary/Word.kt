package com.example.lexicon_memoria.dictionary

/**
 * Represents a dictionary word as homographs
 */
class Word(
    val head: String,
    val homographs: List<Homograph>
) {

    override fun toString(): String {
        return head
    }

    override fun equals(other: Any?): Boolean {
        return when (other is Word) {
            true -> head == other.head
            else -> false
        }
    }

}