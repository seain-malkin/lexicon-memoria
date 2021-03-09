package com.example.lexicon_memoria.module.wordlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lexicon_memoria.databinding.ViewholderWordListBinding
import com.example.lexicon_memoria.module.BaseModel
import com.example.lexicon_memoria.module.ModuleViewHolder

/**
 * View Holder that displays a summary of all user words
 * @property parent The parent view to attach to
 * @property binding The inflated binding object
 */
class WordListViewHolder(
    parent: ViewGroup,
    private val binding: ViewholderWordListBinding = ViewholderWordListBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
) : ModuleViewHolder(binding.root) {

    override fun bind(module: BaseModel) {
        if (module is WordListModel) {
            binding.moduleTitle.text = module.title
            binding.moduleSubtitle.text = "${module.numWords} words"
        }
    }
}