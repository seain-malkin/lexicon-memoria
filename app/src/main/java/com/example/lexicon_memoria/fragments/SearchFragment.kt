package com.example.lexicon_memoria.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.database.entity.Homograph
import com.example.lexicon_memoria.databinding.FragmentSearchBinding
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory
import com.example.lexicon_memoria.viewmodel.SearchViewModel
import com.example.lexicon_memoria.viewmodel.SearchViewModelFactory
import com.google.android.material.textview.MaterialTextView

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    /**
     * Interface for Fragment->Activity communication. The interface must be implemented by the
     * calling activity
     */
    interface SearchFragmentListener {

        /**
         * Triggered when the user clicks the button to add a word to their lexicon.
         *
         * @param word The word object to add
         */
        fun onAddWord(word: DictionaryWord)
    }

    /** @property listener Reference to context that implements [SearchFragmentListener] */
    private var listener: SearchFragmentListener? = null

    /** @property binding Reference to view binding object */
    private lateinit var binding: FragmentSearchBinding

    /** @property query The search term passed from the activity */
    private lateinit var query: String

    /** @property searchViewModel */
    private val searchViewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (requireActivity().application as LexmemApplication).dictionary,
            requireActivity(),
            arguments
        )
    }

    /** @property lexmemViewModel  */
    private val lexmemViewModel: LexmemViewModel by activityViewModels {
        LexmemViewModelFactory(
            (requireActivity().application as LexmemApplication).userWords,
            requireActivity()
        )
    }

    /**
     * Updates view elements to represent search result
     *
     * @param result The word object to display
     */
    private fun displayResult(result: DictionaryWord) {
        binding.textWord.text = result.headword.label

        // Display word definitions
        result.functions.forEach { displayResultFunction(it) }

        // Hide search progress bar and display action button
        binding.progressSearch.visibility = View.GONE
        binding.wordActionButton.visibility = View.VISIBLE
    }

    /**
     * Displays each function label and its definitions
     *
     * @param homograph The function to display
     */
    private fun displayResultFunction(homograph: Homograph) {
        // Add a text view element for the function label
        (layoutInflater.inflate(R.layout.text_view_headline2, null) as MaterialTextView).apply {
            text = homograph.function.label
            binding.definitionContainer.addView(this)
        }

        // Add a text view element for each definition
        homograph.definitions.forEachIndexed { i, def ->
            (layoutInflater.inflate(R.layout.text_view_body, null) as MaterialTextView).apply {
                text = resources.getString(R.string.definition, i, def.definition)
                binding.definitionContainer.addView(this)
            }
        }
    }

    /**
     * @see [Fragment.onCreate]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the search query
        query = arguments?.getString(ARG_QUERY) ?: throw IllegalStateException("Query must be set")
    }

    /**
     * @see [Fragment.onCreateView]
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        binding.textWord.text = query

        // Search for query and store results on response
        searchViewModel.search(query).observe(viewLifecycleOwner, { response ->
            searchViewModel.searchResult.value = response
        })

        // When search complete, display results
        searchViewModel.searchResult.observe(viewLifecycleOwner, { result ->
            result?.let {
                displayResult(it)
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

    /**
     * @see [Fragment.onAttach]
     * Attaches the calling context as a listener. Forces the context to implement
     * [SearchFragmentListener]
     *
     * @param context Calling context
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SearchFragmentListener) {
            listener = context
        } else {
            throw IllegalStateException("$context must implement SearchFragmentListener")
        }
    }

    /**
     * @see [Fragment.onDetach]
     *
     * Release resources
     */
    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    companion object {

        /** Parameter Arguments */
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