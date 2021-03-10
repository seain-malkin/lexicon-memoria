package com.example.lexicon_memoria.fragments.modulelist

import android.content.Context
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

    /**
     * Communicates with activity. Calling activity must implement.
     */
    interface ModuleListListener {

        /**
         * Triggered when a user requests to view a module
         * @param moduleType The module to be viewed
         */
        fun onModuleClick(moduleType: Int)
    }

    private var listener: ModuleListListener? = null

    private val vm: LexmemViewModel by activityViewModels {
        LexmemViewModelFactory((requireActivity().application as LexmemApplication).userWords, this)
    }

    private lateinit var binding: FragmentModuleListBinding
    private val adapter = ModuleListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentModuleListBinding.inflate(layoutInflater, container, false)

        adapter.listener = listener
        binding.moduleListContainer.adapter = adapter
        binding.moduleListContainer.layoutManager = LinearLayoutManager(binding.root.context)

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

    /**
     * Reference the activity context and ensure interface implementation.
     *
     * @see [Fragment.onAttach]
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Enforce interface implementation
        if (context is ModuleListListener) {
            listener = context
        } else {
            throw IllegalStateException("Activity must implement ModuleListListener interface.")
        }
    }

    /**
     * Remove reference to activity.
     *
     * @see [Fragment.onDetach]
     */
    override fun onDetach() {
        super.onDetach()

        listener = null
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