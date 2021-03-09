package com.example.lexicon_memoria.module.wordlist

import com.example.lexicon_memoria.module.BaseModel

/**
 * A module that displays a list of words
 * @see [BaseModel]
 */
class WordListModel(
    override var title: String,
    override var viewType: Int = BaseModel.VIEW_LIST
) : BaseModel