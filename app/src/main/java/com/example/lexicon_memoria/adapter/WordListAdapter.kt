package com.example.lexicon_memoria.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.viewholder.WordListItemViewHolder

/**
 * An adapter to display a list of words
 * @property words A list of words
 */
class WordListAdapter(
    val words: MutableList<WordWithScore> = mutableListOf()
) : RecyclerView.Adapter<WordListItemViewHolder>() {

    /**
     * @see [RecyclerView.Adapter.onCreateViewHolder]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListItemViewHolder {
        return WordListItemViewHolder(parent)
    }

    /**
     * @see [RecyclerView.Adapter.onBindViewHolder]
     */
    override fun onBindViewHolder(holder: WordListItemViewHolder, position: Int) {
        holder.bind(words[position])
    }

    /**
     * @see [RecyclerView.Adapter.getItemCount]
     */
    override fun getItemCount(): Int {
        return words.size
    }
}