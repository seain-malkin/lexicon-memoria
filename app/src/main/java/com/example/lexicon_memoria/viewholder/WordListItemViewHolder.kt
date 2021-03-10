package com.example.lexicon_memoria.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.database.entity.WordWithScore
import com.example.lexicon_memoria.databinding.ViewholderWordListItemBinding

class WordListItemViewHolder(
    parent: ViewGroup,
    private val binding: ViewholderWordListItemBinding = ViewholderWordListItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WordWithScore) {
        binding.title.text = item.word.headword.label
    }
}