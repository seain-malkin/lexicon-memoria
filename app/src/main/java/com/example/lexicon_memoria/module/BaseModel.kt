package com.example.lexicon_memoria.module

/**
 * The base data model for each module
 * @property title The title of the module
 * @property viewType The associated view type constant value
 */
interface BaseModel {
    val title: String
    val viewType: Int

    companion object {
        const val VIEW_LIST = 0
        const val VIEW_DAILY = 1
        const val VIEW_MONTHLY = 2
    }
}