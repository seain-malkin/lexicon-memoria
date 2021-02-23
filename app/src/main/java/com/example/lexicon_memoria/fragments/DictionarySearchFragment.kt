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
import com.example.lexicon_memoria.databinding.FragmentDictionarySearchBinding
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModel
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModelFactory

class DictionarySearchFragment : Fragment() {

    private lateinit var binding: FragmentDictionarySearchBinding

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
        binding = FragmentDictionarySearchBinding.inflate(inflater, container, false)

        // Search button click
        binding.buttonSearch.setOnClickListener {
            // Only search if not empty field
            if (!TextUtils.isEmpty(binding.searchKey.text)) {
                dsVM.search(binding.searchKey.text.toString())
            }
        }

        // When user changes search key, remove last search result
        binding.searchKey.addTextChangedListener {
            dsVM.searchKeyChanged.value = true
        }

        // Update UI when search status changes
        dsVM.searchInProgress.observe(viewLifecycleOwner, { inProgress ->
            binding.buttonSearch.isEnabled = !inProgress
        })

        return binding.root
    }
}