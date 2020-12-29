package com.example.lexicon_memoria.dictionary

interface DictionaryWord {

    /**
     * Formats the object to be printed
     */
    override fun toString() : String

    /**
     * Compares itself with another object and returns a boolean
     * indicating equality
     * @param[other] The object to compare to
     */
    override fun equals(other: Any?) : Boolean
}