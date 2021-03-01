package com.example.lexicon_memoria.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Lexicon(
    @Embedded val user: UserEntity,
    @Relation(
        entity = HeadwordEntity::class,
        parentColumn = UserEntity.Columns.id,
        entityColumn = HeadwordEntity.Columns.id,
        associateBy = Junction(
            value = UserWordEntity::class,
            parentColumn = UserEntity.foreignKey,
            entityColumn = HeadwordEntity.foreignKey
        )
    ) val words: List<DictionaryWord>
)

data class DictionaryWord(
    @Embedded val headword: HeadwordEntity,
    @Relation(
        entity = WordFunctionEntity::class,
        parentColumn = HeadwordEntity.Columns.id,
        entityColumn = WordFunctionEntity.Columns.headWordId
    ) val functions: List<Homograph>,
    @Relation(
        entity = PronunciationEntity::class,
        parentColumn = HeadwordEntity.Columns.id,
        entityColumn = PronunciationEntity.Columns.headwordId
    ) val pronunciation: PronunciationEntity? = null
)

data class Homograph(
    @Embedded val function: WordFunctionEntity,
    @Relation(
        parentColumn = WordFunctionEntity.Columns.id,
        entityColumn = DefinitionEntity.Columns.wordFunctionId
    )
    val definitions: List<DefinitionEntity>
)