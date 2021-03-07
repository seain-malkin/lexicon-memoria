package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.databinding.FragmentModuleListBinding
import com.example.lexicon_memoria.databinding.ViewholderAllWordsBinding
import com.example.lexicon_memoria.module.BaseModule
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory
import com.example.lexicon_memoria.viewmodel.UserViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ModuleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleListFragment : Fragment() {

    private val lexmemVM: LexmemViewModel by viewModels {
        LexmemViewModelFactory((requireActivity().application as LexmemApplication).userWords, this)
    }

    private lateinit var binding: FragmentModuleListBinding
    private lateinit var adapter: ModuleListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentModuleListBinding.inflate(layoutInflater, container, false)

        adapter = ModuleListAdapter()
        binding.moduleListContainer.adapter = adapter
        binding.moduleListContainer.layoutManager = LinearLayoutManager(
            binding.root.context, LinearLayoutManager.VERTICAL, false
        )

        lexmemVM.modules.observe(viewLifecycleOwner, { modules ->
            adapter.data = modules
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ModuleListFragment.
         */
        @JvmStatic
        fun newInstance() = ModuleListFragment()

        const val VIEW_ALL_WORDS = 0
    }

    inner class ModuleListAdapter(
        var data: List<BaseModule> = listOf()
    ) : RecyclerView.Adapter<ModuleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
            val li = layoutInflater
            return when(viewType) {
                VIEW_ALL_WORDS -> AllWordsViewHolder(ViewholderAllWordsBinding.inflate(li))
                else -> throw IllegalStateException("View type with no view holder.")
            }
        }

        override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            return VIEW_ALL_WORDS
        }
    }

    abstract inner class ModuleViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(
        itemView
    ) {
        abstract fun bind(module: BaseModule)
    }

    private inner class AllWordsViewHolder(
        val binding: ViewholderAllWordsBinding
    ) : ModuleViewHolder(binding.root) {


        override fun bind(module: BaseModule) {
            binding.moduleTitle.text = module.title
        }
    }
}