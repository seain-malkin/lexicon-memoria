package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Inflate fragment view
        val view = inflater.inflate(R.layout.fragment_dictionary_result, container, false)

        // Update recycler list when search results change
        dictionarySearchVM.lookupResult.observe(viewLifecycleOwner, { result ->
            // TODO
        })

        return view
    }
}