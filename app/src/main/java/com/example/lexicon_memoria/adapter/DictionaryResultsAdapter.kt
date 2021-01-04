package com.example.lexicon_memoria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.adapter.DictionaryResultsAdapter.ResultViewHolder
import com.example.lexicon_memoria.dictionary.Homograph

/**
 * Recyclerview adapter to display dictionary result list
 * @author Seain Malkin (dev@seain.me)
 * @property[listener] The context listener
 */
class DictionaryResultsAdapter(
        private val listener: DictionaryResultsAdapterListener
) : ListAdapter<Homograph, ResultViewHolder>(ResultComparator()) {

    /**
     * Listener interface for fragment communication
     */
    interface DictionaryResultsAdapterListener {

        /**
         * Triggered when the user selects a search result
         * @param[result] The selected result
         */
        fun onDictionaryResultSelected(result: Homograph)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.dictionary_result_homograph, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Represents the view of each result
     * @param[itemView] The inflated view
     */
    inner class ResultViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val label: TextView = itemView.findViewById(R.id.label)
        private val defList: ListView = itemView.findViewById(R.id.definition_list)
        private val listAdapter = ArrayAdapter<String>(
                itemView.context, R.layout.definition_list_item
        )

        init {
            defList.adapter = listAdapter
        }

        fun bind(item: Homograph) {
            label.text = item.label
            listAdapter.clear()
            listAdapter.addAll(item.definitions)
            listAdapter.notifyDataSetChanged()
        }
    }

    class ResultComparator : DiffUtil.ItemCallback<Homograph>() {

        override fun areContentsTheSame(oldItem: Homograph, newItem: Homograph): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Homograph, newItem: Homograph): Boolean {
            return oldItem == newItem
        }
    }
}