package com.example.lexicon_memoria.dictionary

data class Homograph(
    val label: String,
    val definitions: List<String>
) {
    override fun toString(): String {
        return label
    }
}