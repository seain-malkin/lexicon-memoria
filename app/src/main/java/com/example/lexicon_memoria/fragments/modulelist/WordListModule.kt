package com.example.lexicon_memoria.fragments.modulelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lexicon_memoria.LexmemActivity
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.fragments.ViewWordFragment
import com.example.lexicon_memoria.fragments.WordListFragment

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
        private const val ARG_ACTION = "word_list_module:action"
        private const val ARG_ENTITY_ID = "word_list_module:entity_id"
        private const val ACTION_VIEW_ALL = 0
        private const val ACTION_CHIP_CLICK = 1

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

        fun getChipClickBundle(id: Long): Bundle {
            return Bundle().apply {
                putInt(ARG_ACTION, ACTION_CHIP_CLICK)
                putLong(ARG_ENTITY_ID, id)
            }
        }

        fun fragmentFactory(bundle: Bundle?): Fragment {
            return when(bundle?.let { it.getInt(ARG_ACTION) } ?: ACTION_VIEW_ALL) {
                ACTION_VIEW_ALL -> WordListFragment.newInstance()
                ACTION_CHIP_CLICK -> bundle?.let {
                    ViewWordFragment.newInstance(it.getLong(ARG_ENTITY_ID, 0L))
                } ?: throw IllegalStateException("The entity id is not defined.")
                else -> throw IllegalStateException("Unknown action requested.")
            }
        }
    }
}