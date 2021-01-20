package com.example.lexicon_memoria.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithFunctionalDefinitions(
    @Embedded val headword: HeadWordEntity,
    @Relation(
        entity = SpeechFunctionEntity::class,
        parentColumn = "label",
        entityColumn = "word"
    )
    val functions: List<FunctionWithDefinitions>
)

data class FunctionWithDefinitions(
    @Embedded val function: SpeechFunctionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "function_id"
    )
    val definitions: List<DefinitionEntity>
)