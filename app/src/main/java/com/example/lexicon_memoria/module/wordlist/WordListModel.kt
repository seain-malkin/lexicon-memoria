package com.example.lexicon_memoria.module.wordlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.module.BaseModel

/**
 * A module that displays a list of words
 * @see [BaseModel]
 */
data class WordListModel(
    override val title: String,
    val numWords: Int,
    val recentWords: List<WordWithScore>,
    override val viewType: Int = BaseModel.VIEW_LIST
) : BaseModel {

    companion object {

        fun awaitData(
            title: String,
            numWords: LiveData<Int>,
            recentWords: LiveData<List<WordWithScore>>
        ): LiveData<WordListModel> {
            return MediatorLiveData<WordListModel>().apply {
                var numWordsValue: Int? = null
                var recentWordsValue: List<WordWithScore>? = null

                fun update() {
                    // Only update value if all data has been collected
                    if (numWordsValue != null && recentWordsValue != null) {
                        value = WordListModel(title, numWordsValue!!, recentWordsValue!!)

                        // Recent all data so collection can begin again
                        numWordsValue = null
                        recentWordsValue = null
                    }
                }

                addSource(numWords) {
                    numWordsValue = it
                    update()
                }

                addSource(recentWords) {
                    recentWordsValue = it
                    update()
                }
            }
        }
    }
}