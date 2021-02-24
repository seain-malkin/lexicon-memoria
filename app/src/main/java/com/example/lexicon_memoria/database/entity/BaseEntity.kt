package com.example.lexicon_memoria.database.entity

/**
 * Base table entity for database. Requires all tables to use an ID for the primary index
 */
abstract class BaseEntity {
    abstract var id: Long

    companion object {
        const val id = "id"
    }
}