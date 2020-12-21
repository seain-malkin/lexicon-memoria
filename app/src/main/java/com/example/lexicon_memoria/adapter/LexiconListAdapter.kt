package com.example.lexicon_memoria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.adapter.LexiconListAdapter.LexiconViewHolder

/**
 * Recyclerview adapter to display a list of lexicons
 * @author Seain Malkin (dev@seain.me)
 */
class LexiconListAdapter
    : ListAdapter<LexiconEntity, LexiconViewHolder>(LexiconComparator()) {

    /** @see[ListAdapter.onCreateViewHolder] */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LexiconViewHolder {
        return LexiconViewHolder.create(parent)
    }

    /** @see[ListAdapter.onBindViewHolder] */
    override fun onBindViewHolder(holder: LexiconViewHolder, position: Int) {
        holder.bind(getItem(position).label)
    }

    /**
     * Represents the [View] used to display each lexicon in the list
     * @property[itemView] The inflated view
     */
    class LexiconViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        /**
         * Binds the item data to the view
         * @param[text]
         */
        fun bind(text: String?) {

        }

        companion object {
            /**
             * Returns the view holder with the inflated view. This can be extended to allow
             * different views depending on the object being displayed
             * @param[parent] The parent of the view
             */
            fun create(parent: ViewGroup): LexiconViewHolder {
                // Inflates the view and instantiates the view holder
                return LexiconViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.lexicon_list_view, parent, false
                ))
            }
        }
    }

    /**
     * Compares lexicon objects for equality
     * @see[DiffUtil.ItemCallback]
     */
    class LexiconComparator : DiffUtil.ItemCallback<LexiconEntity>() {

        /** @see[DiffUtil.ItemCallback.areItemsTheSame] */
        override fun areItemsTheSame(oldItem: LexiconEntity, newItem: LexiconEntity): Boolean {
            return oldItem == newItem
        }

        /** @see[DiffUtil.ItemCallback.areContentsTheSame] */
        override fun areContentsTheSame(oldItem: LexiconEntity, newItem: LexiconEntity): Boolean {
            return oldItem.label == newItem.label
        }
    }
}