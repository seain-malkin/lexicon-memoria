package com.example.lexicon_memoria.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Lexicon(
    @Embedded val lexicon: LexiconEntity,
    @Relation(
        entity = HeadwordEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LexiconWordEntity::class,
            parentColumn = "lexicon_id",
            entityColumn = "headword_id"
        )
    ) val words: List<Homographs>
)

data class Homographs(
    @Embedded val headword: HeadwordEntity,
    @Relation(
        entity = WordFunctionEntity::class,
        parentColumn = "id",
        entityColumn = "headword_id"
    ) val functions: List<Homograph>
)

data class Homograph(
    @Embedded val function: WordFunctionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "word_function_id"
    )
    val definitions: List<DefinitionEntity>
)