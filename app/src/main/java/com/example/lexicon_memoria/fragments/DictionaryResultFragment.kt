package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModel
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModelFactory

class DictionaryResultFragment : Fragment() {

    /** Shared View Model */
    private val dictionarySearchVM: DictionarySearchViewModel by activityViewModels {
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

        val view = inflater.inflate(R.layout.fragment_dictionary_result, container, false)
        val headwordText: TextView = view.findViewById(R.id.headword)

        // Update UI when search result completes
        dictionarySearchVM.searchResults.observe(viewLifecycleOwner, { results ->
            // Update search progress so UI components can update
            dictionarySearchVM.searchInProgress.value = false
            // Bind search result to biew
            headwordText.text = "${results[0]}"
        })

        return view
    }
}