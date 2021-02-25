package com.example.lexicon_memoria.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.databinding.FragmentSearchBinding
import com.example.lexicon_memoria.viewmodel.SearchViewModel
import com.example.lexicon_memoria.viewmodel.SearchViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var query: String

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            (requireActivity().application as LexmemApplication).dictionary,
            requireActivity(),
            arguments
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        query = arguments?.getString(ARG_QUERY) ?: throw IllegalStateException("Query must be set")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        binding.testView.text = query

        searchViewModel.search(query).observe(viewLifecycleOwner, { response ->
            Log.i("Response", "$response")
        })

        return binding.root
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