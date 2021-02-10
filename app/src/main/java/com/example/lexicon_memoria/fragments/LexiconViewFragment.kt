package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.viewmodel.LexiconViewModel
import com.example.lexicon_memoria.viewmodel.LexiconViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [LexiconViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LexiconViewFragment : Fragment() {
    private var userId: Long = 0
    private var lexiconLabel: String? = null

    private val lexiconViewModel: LexiconViewModel by activityViewModels {
        LexiconViewModelFactory(
                (requireActivity().application as LexmemApplication).dictionary,
                requireActivity(),
                arguments
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getLong(ARG_USERNAME)
            lexiconLabel = it.getString(ARG_LEXICON_LABEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lexicon, container, false)
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_LEXICON_LABEL = "lexicon_label"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param[username] Username identifier
         * @param[lexiconLabel] Lexicon label identifier
         * @return A new instance of fragment LexiconFragment.
         */
        @JvmStatic
        fun newInstance(userId: Long, lexiconLabel: String) =
                LexiconViewFragment().apply {
                    arguments = Bundle().apply {
                        putLong(ARG_USERNAME, userId)
                        putString(ARG_LEXICON_LABEL, lexiconLabel)
                    }
                }
    }
}