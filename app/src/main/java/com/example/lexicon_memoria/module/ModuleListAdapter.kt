package com.example.lexicon_memoria.module

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.module.wordlist.WordListViewHolder

/**
 * An adapter associated with the module list
 * @property modules The data to display
 */
class ModuleListAdapter(
    var modules: MutableList<BaseModel> = mutableListOf()
) : RecyclerView.Adapter<ModuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        return when (viewType) {
            BaseModel.VIEW_LIST -> WordListViewHolder(parent)
            else -> throw IllegalStateException("View type has no corresponding view.")
        }
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position])
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    override fun getItemViewType(position: Int): Int {
        return modules[position].viewType
    }
}