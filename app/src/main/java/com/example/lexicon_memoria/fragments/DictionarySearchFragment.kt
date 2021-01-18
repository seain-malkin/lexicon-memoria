package com.example.lexicon_memoria.fragments

import android.os.Bundle
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModel
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModelFactory

class DictionarySearchFragment : Fragment() {

    /** Shared view model */
    private val dsVM: DictionarySearchViewModel by activityViewModels {
        DictionarySearchViewModelFactory(
            (requireActivity().application as LexmemApplication).dictionary,
            requireActivity(),
            arguments
        )
    }

    /**
     * Inflate the view and bind data and observers
     * @see[Fragment.onCreateView]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary_search, container, false)

        val searchText: EditText = view.findViewById(R.id.search_key)
        val searchButton: Button = view.findViewById(R.id.button_search)

        // Search button click
        searchButton.setOnClickListener {
            // Only search if not empty field
            if (!TextUtils.isEmpty(searchText.text)) {
                dsVM.search(searchText.text.toString())
            }
        }

        // When user changes search key, remove last search result
        searchText.addTextChangedListener {
            dsVM.searchKeyChanged.value = true
        }

        // Update UI when search status changes
        dsVM.searchInProgress.observe(viewLifecycleOwner, { inProgress ->
            searchButton.isEnabled = !inProgress
        })

        return view
    }
}