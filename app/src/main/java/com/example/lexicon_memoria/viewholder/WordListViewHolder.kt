package com.example.lexicon_memoria.viewholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lexicon_memoria.LexmemActivity
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.databinding.ViewholderWordListBinding
import com.example.lexicon_memoria.fragments.modulelist.BaseModule
import com.example.lexicon_memoria.fragments.modulelist.ModuleListFragment.ModuleListListener
import com.example.lexicon_memoria.fragments.modulelist.WordListModule
import com.google.android.material.chip.Chip

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
            val context = binding.root.context
            binding.moduleTitle.text = module.title
            binding.moduleSubtitle.text = context.getString(
                R.string.word_count, module.numWords
            )
            //binding.root.setOnClickListener { onClick.invoke(module.moduleType, null) }

            // Attach a chip view for each recent word
            val inflater = LayoutInflater.from(context)
            module.recentWords.forEach { word ->
                (inflater.inflate(R.layout.chip_recent, null) as Chip).apply {
                    text = word.word.headword.label
                    // Set event when a chip is clicked
                    setOnClickListener {
                        onClick.invoke(
                            module.moduleType,
                            WordListModule.getChipClickBundle(word.userWord.headwordId)
                        )
                    }
                    binding.recentChips.addView(this)
                }
            }
        } else {
            throw IllegalStateException("The wrong module was injected.")
        }
    }
}