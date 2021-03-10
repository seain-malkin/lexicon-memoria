package com.example.lexicon_memoria.fragments.modulelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.databinding.FragmentModuleListBinding
import com.example.lexicon_memoria.adapter.ModuleListAdapter
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [ModuleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleListFragment : Fragment() {

    private val vm: LexmemViewModel by activityViewModels {
        LexmemViewModelFactory((requireActivity().application as LexmemApplication).userWords, this)
    }

    private lateinit var binding: FragmentModuleListBinding
    private val adapter = ModuleListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentModuleListBinding.inflate(layoutInflater, container, false)

        binding.moduleListContainer.adapter = adapter
        binding.moduleListContainer.layoutManager = LinearLayoutManager(
            binding.root.context, LinearLayoutManager.VERTICAL, false
        )

        WordListModule.awaitData("View all words", vm.totalWords, vm.recentWords)
            .observe(viewLifecycleOwner, { model ->
                val index = adapter.modules.indexOfFirst { it.viewType == BaseModule.VIEW_LIST }
                when (index == -1) {
                    true -> {
                        adapter.modules.add(model)
                        adapter.notifyItemInserted(adapter.modules.size -1)
                    }
                    false -> {
                        adapter.modules[index] = model
                        adapter.notifyItemChanged(index)
                    }
                }
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
    }
}