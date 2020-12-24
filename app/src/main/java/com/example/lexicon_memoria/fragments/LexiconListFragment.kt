package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.adapter.LexiconListAdapter
import com.example.lexicon_memoria.adapter.LexiconListAdapter.LexiconListAdapterListener
import com.example.lexicon_memoria.viewmodel.LexiconListViewModel
import com.example.lexicon_memoria.viewmodel.LexiconListViewModelFactory

private const val ARG_USERNAME = "username"

/**
 * A simple [Fragment] subclass.
 * Use the [LexiconListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LexiconListFragment : Fragment() {
    private var username: String? = null

    /** View Model for displaying list of lexicons */
    private val lexiconListViewModel: LexiconListViewModel by activityViewModels {
        LexiconListViewModelFactory(
                (requireActivity().application as LexmemApplication).lexicons,
                requireActivity(),
                arguments
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lexicon_list, container, false)

        // Setup the recycler view to display the lexicon list
        val recycler: RecyclerView = view.findViewById(R.id.rvLexList)

        // Create adapter with activity as listener
        val adapter = LexiconListAdapter(activity as LexiconListAdapterListener)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)

        // Define an observer on the lexicon list
        lexiconListViewModel.all.observe(viewLifecycleOwner, { lexicons ->
            lexicons?.let { adapter.submitList(it) }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param[username] The username associated with displayed lexicons
         * @return A new instance of fragment LexiconListFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
            LexiconListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}