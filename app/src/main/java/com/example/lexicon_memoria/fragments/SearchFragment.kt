package com.example.lexicon_memoria.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.databinding.FragmentSearchBinding
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory
import com.example.lexicon_memoria.viewmodel.SearchViewModel
import com.example.lexicon_memoria.viewmodel.SearchViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    interface SearchFragmentListener {
        fun onAddWord(word: DictionaryWord)
    }

    private var listener: SearchFragmentListener? = null
    private lateinit var binding: FragmentSearchBinding
    private lateinit var query: String

    private val searchViewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (requireActivity().application as LexmemApplication).dictionary,
            requireActivity(),
            arguments
        )
    }

    private val lexmemViewModel: LexmemViewModel by activityViewModels {
        LexmemViewModelFactory(
            (requireActivity().application as LexmemApplication).userWords,
            requireActivity()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        query = arguments?.getString(ARG_QUERY) ?: throw IllegalStateException("Query must be set")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        binding.textWord.text = query

        // When search complete, display results
        searchViewModel.search(query).observe(viewLifecycleOwner, { response ->
            searchViewModel.searchResult.value = response
        })

        searchViewModel.searchResult.observe(viewLifecycleOwner, { result ->
            result?.let {
                binding.textWord.text = result.headword.label
                binding.textFunction.text = result.functions[0].function.label
            }
        })

        // When add button clicked, add word to user list
        binding.wordActionButton.setOnClickListener {
            searchViewModel.searchResult.value?.let {
                lexmemViewModel.addWord(it)
                listener?.onAddWord(it)
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SearchFragmentListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement SearchFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    companion object {
        private const val ARG_QUERY = "search_fragment:query"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param query The query to search for
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance(query: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }
}