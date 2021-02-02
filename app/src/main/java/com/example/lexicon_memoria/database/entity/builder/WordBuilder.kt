package com.example.lexicon_memoria.database.entity.builder

import com.example.lexicon_memoria.database.entity.*
import java.util.*

/**
 * Helper class for building a DictionaryWord object
 * @param headword The headword object
 */
class WordBuilder(
    var headword: HeadwordEntity
) {
    /**
     * Secondary constructor
     * @see [HeadwordEntity] for param description
     */
    constructor(
        name: String,
        source: String = SOURCE_UNDEFINED,
        sourceId: String? = null,
        sortIndex: String? = null
    ) : this(HeadwordEntity(name, source, sourceId, sortIndex))

    /**
     * @property functions The word functions associated with the headword
     */
    private val functions: MutableList<WordFunctionEntity> = mutableListOf()

    /**
     * @property definitions Definitions for word functions mapped to the word functional label
     */
    private val definitions: MutableMap<String, MutableList<DefinitionEntity>> = mutableMapOf()

    /**
     * Creates the DictionaryWord object and returns it
     * @return The created object
     */
    fun build(): DictionaryWord {
        /** Each word function is transformed into a [Homograph] with the definition list */
        return DictionaryWord(headword, functions.mapNotNull { fl ->
            definitions[fl.name]?.let { Homograph(fl, it.toList()) }
        })
    }

    /**
     * Attaches a [WordFunctionEntity] to the object
     * @param functionLabel The name of the function
     */
    fun attach(functionLabel: String) {
        // enforce lowercase
        val label = cleanKey(functionLabel)

        // Ensure labels are unique by checking the definition map first
        if (!definitions.contains(label)) {
            definitions[label] = mutableListOf()
            functions.add(WordFunctionEntity(headword.id, label))
        }
    }

    /**
     * Attaches a definition to the word function label. If the function label doesn't exist it
     * will be created
     * @param functionLabel The functional label this definition belongs to
     * @param definition The definition
     * @param order The sorting order. If left at 0, the order will be incremented
     */
    fun attach(functionLabel: String, definition: String, order: Int = 0) {
        // Ensure the function label exists
        attach(functionLabel)

        // Add the definition and define the order based on the list size
        definitions[cleanKey(functionLabel)]?.run {
            add(DefinitionEntity(0, definition, when(order == 0) {true -> size + 1 else -> order}))
        }
    }

    /**
     * Attaches a collection of definitions to a function label. The function label will be
     * created if it doesn't exist. The order is implied by the list order
     * @param functionLabel The functional label the definitions belong to
     * @param definitions The list of definitions to apply
     */
    fun attach(functionLabel: String, definitions: List<String>) {
        // Attach each definition using the same order in the list
        definitions.forEachIndexed { i, s -> attach(functionLabel, s, i) }
    }

    /**
     * Applies rules to ensure similar labels aren't duplicated
     * @param label The string to parse
     * @return The cleaned string
     */
    private fun cleanKey(key: String): String {
        return key.toLowerCase(Locale.ROOT)
    }

    companion object {
        const val SOURCE_UNDEFINED = "undefined"
    }
}