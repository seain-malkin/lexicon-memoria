package com.example.lexicon_memoria.dictionary

class Homograph(
    val label: String,
    val definitions: List<String>
) {
    override fun equals(other: Any?): Boolean {
        return when(other is Homograph) {
            true -> label == other.label
            else -> false
        }
    }
}