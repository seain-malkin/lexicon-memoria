package com.example.lexicon_memoria.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lexicon_memoria.databinding.ViewholderWordListBinding
import com.example.lexicon_memoria.fragments.modulelist.BaseModule
import com.example.lexicon_memoria.fragments.modulelist.ModuleListFragment.ModuleListListener
import com.example.lexicon_memoria.fragments.modulelist.WordListModule

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

    override fun bind(module: BaseModule, onClick: ClickHandler) {
        if (module is WordListModule) {
            binding.moduleTitle.text = module.title
            binding.moduleSubtitle.text = "${module.numWords} words"
            binding.root.setOnClickListener { onClick.invoke(module.moduleType) }
        } else {
            throw IllegalStateException("The wrong module was injected.")
        }
    }
}