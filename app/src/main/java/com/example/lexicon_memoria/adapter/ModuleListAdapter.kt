package com.example.lexicon_memoria.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.fragments.modulelist.BaseModule
import com.example.lexicon_memoria.fragments.modulelist.ModuleListFragment.ModuleListListener
import com.example.lexicon_memoria.viewholder.ModuleViewHolder
import com.example.lexicon_memoria.viewholder.WordListViewHolder

/**
 * An adapter associated with the module list
 * @property modules The data to display
 */
class ModuleListAdapter(
    var modules: MutableList<BaseModule> = mutableListOf()
) : RecyclerView.Adapter<ModuleViewHolder>() {

    var listener: ModuleListListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        return when (viewType) {
            BaseModule.VIEW_LIST -> WordListViewHolder(parent)
            else -> throw IllegalStateException("View type has no corresponding view.")
        }
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position]) { i -> listener?.onModuleClick(i) }
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    override fun getItemViewType(position: Int): Int {
        return modules[position].viewType
    }
}