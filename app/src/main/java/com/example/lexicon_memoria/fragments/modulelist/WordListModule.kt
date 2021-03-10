package com.example.lexicon_memoria.fragments.modulelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lexicon_memoria.LexmemActivity
import com.example.lexicon_memoria.database.entity.WordWithScore

/**
 * A module that displays a list of words
 * @see [BaseModule]
 */
data class WordListModule(
    override val title: String,
    val numWords: Int,
    val recentWords: List<WordWithScore>,
    override val viewType: Int = BaseModule.VIEW_LIST,
    override val moduleType: Int = LexmemActivity.MODULE_WORD_LIST
) : BaseModule {

    companion object {

        fun awaitData(
            title: String,
            numWords: LiveData<Int>,
            recentWords: LiveData<List<WordWithScore>>
        ): LiveData<WordListModule> {
            return MediatorLiveData<WordListModule>().apply {
                var numWordsValue: Int? = null
                var recentWordsValue: List<WordWithScore>? = null

                fun update() {
                    // Only update value if all data has been collected
                    if (numWordsValue != null && recentWordsValue != null) {
                        value = WordListModule(title, numWordsValue!!, recentWordsValue!!)

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