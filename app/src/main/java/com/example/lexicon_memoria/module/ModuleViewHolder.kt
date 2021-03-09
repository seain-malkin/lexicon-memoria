package com.example.lexicon_memoria.module

import android.view.View
import androidx.recyclerview.widget.RecyclerView

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
    abstract fun bind(module: BaseModel)
}