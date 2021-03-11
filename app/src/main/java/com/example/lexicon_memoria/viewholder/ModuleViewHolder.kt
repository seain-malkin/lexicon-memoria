package com.example.lexicon_memoria.viewholder

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.fragments.modulelist.BaseModule

typealias ClickHandler = (Int, Bundle?) -> Unit

/**
 * The base view holder that all modules extend from.
 */
abstract class ModuleViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(
    itemView
) {
    /**
     * Binds the module data to the module view holder
     * @param module The module data object
     */
    abstract fun bind(module: BaseModule, onClick: ClickHandler)
}