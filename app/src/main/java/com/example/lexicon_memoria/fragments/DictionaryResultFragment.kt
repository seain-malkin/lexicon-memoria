package com.example.lexicon_memoria.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.databinding.FragmentDictionaryResultBinding
import com.example.lexicon_memoria.view.compound.WordLayout
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModel
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModelFactory
import java.lang.IllegalStateException

class DictionaryResultFragment : Fragment() {

    interface DictionaryResultListener {

        fun onSaveWord(headwordId: Long)
    }

    private var listener: DictionaryResultListener? = null

    private lateinit var binding: FragmentDictionaryResultBinding

    /** Shared View Model */
    private val dsVM: DictionarySearchViewModel by activityViewModels {
        DictionarySearchViewModelFactory(
            (requireActivity().application as LexmemApplication).dictionary,
            requireActivity(),
            arguments
        )
    }

    /**
     * Inflate view and bind data and observers
     * @see[Fragment.onCreateView]
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate fragment view
        binding = FragmentDictionaryResultBinding.inflate(inflater, container, false)

        // Update recycler list when search results change
        dsVM.lookupResult.observe(viewLifecycleOwner, { result ->
            binding.wordLayout.word = result
        })

        // Remove the last search result when search key changes
        dsVM.searchKeyChanged.observe(viewLifecycleOwner, { changed ->
            if (changed) {
                binding.wordLayout.showPlaceholder()
            }
        })

        // Attach click event to Save Button that informs activity of chosen word
        binding.buttonAdd.setOnClickListener {
            dsVM.lookupResult.value?.let { listener?.onSaveWord(it.headword.id) }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is DictionaryResultListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement DictionaryResultListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}