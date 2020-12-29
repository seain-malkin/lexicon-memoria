package com.example.lexicon_memoria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.example.lexicon_memoria.adapter.DictionaryResultsAdapter.ResultViewHolder

/**
 * Recyclerview adapter to display dictionary result list
 * @author Seain Malkin (dev@seain.me)
 * @property[listener] The context listener
 */
class DictionaryResultsAdapter(
        private val listener: DictionaryResultsAdapterListener
) : ListAdapter<DictionaryWord, ResultViewHolder>(ResultComparator()) {

    /**
     * Listener interface for fragment communication
     */
    interface DictionaryResultsAdapterListener {

        /**
         * Triggered when the user selects a search result
         * @param[result] The selected result
         */
        fun onDictionaryResultSelected(result: DictionaryWord)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.dictionary_word_card_view, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Represents the view of each result
     * @param[itemView] The inflated view
     */
    inner class ResultViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val headword: TextView = itemView.findViewById(R.id.headword)

        fun bind(result: DictionaryWord) {
            headword.text = "$result"
        }
    }

    class ResultComparator : DiffUtil.ItemCallback<DictionaryWord>() {

        override fun areContentsTheSame(oldItem: DictionaryWord, newItem: DictionaryWord): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: DictionaryWord, newItem: DictionaryWord): Boolean {
            return oldItem == newItem
        }
    }
}