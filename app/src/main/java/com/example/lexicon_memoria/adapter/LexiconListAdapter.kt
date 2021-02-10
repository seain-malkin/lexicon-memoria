package com.example.lexicon_memoria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.adapter.LexiconListAdapter.LexiconViewHolder

/**
 * Recyclerview adapter to display a list of lexicons
 * @author Seain Malkin (dev@seain.me)
 * @property[listener] The context listener
 */
class LexiconListAdapter(
        private val listener: LexiconListAdapterListener
) : ListAdapter<LexiconEntity, LexiconViewHolder>(LexiconComparator()) {

    /**
     * Listener interface for fragment/activity communication
     */
    interface LexiconListAdapterListener {

        /**
         * Triggered when a list item is clicked
         * @param[label] The lexicon label
         */
        fun onLexiconListItemClick(label: String)
    }

    /** @see[ListAdapter.onCreateViewHolder] */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LexiconViewHolder {
        return LexiconViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.lexicon_list_view, parent, false
        ))
    }

    /** @see[ListAdapter.onBindViewHolder] */
    override fun onBindViewHolder(holder: LexiconViewHolder, position: Int) {
        holder.bind(getItem(position).name)
    }

    /**
     * Represents the [View] used to display each lexicon in the list
     * @param[itemView] The inflated view
     */
    inner class LexiconViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val label: TextView = itemView.findViewById(R.id.label)

        init {
            // Attach even listener that informs external listener
            itemView.findViewById<CardView>(R.id.card).setOnClickListener {
                listener.onLexiconListItemClick(label.text.toString())
            }
        }

        /**
         * Binds the item data to the view
         * @param[text]
         */
        fun bind(text: String?) {
            label.text = text
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
            return oldItem.name == newItem.name
        }
    }
}