package com.example.lexicon_memoria.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.adapter.DictionaryResultsAdapter
import com.example.lexicon_memoria.dictionary.DictionaryWord
import com.example.lexicon_memoria.dictionary.Homograph
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModel
import com.example.lexicon_memoria.viewmodel.DictionarySearchViewModelFactory

class DictionaryResultFragment :
        DictionaryResultsAdapter.DictionaryResultsAdapterListener,
        Fragment() {

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

        // Create adapter with fragment as listener
        val adapter = DictionaryResultsAdapter(this)

        // Setup recycler list
        val recycler: RecyclerView = view.findViewById(R.id.rv_results)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)

        // Update recycler list when search results change
        dictionarySearchVM.lookupResult.observe(viewLifecycleOwner, { result ->
            //adapter.submitList(result)
            Log.i("Lookup", "$result")
        })

        return view
    }

    override fun onDictionaryResultSelected(result: Homograph) {
        Log.i("Result Selected", "$result")
    }
}