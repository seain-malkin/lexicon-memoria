package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lexicon_memoria.R

private const val ARG_USERNAME = "username"
private const val ARG_LEXICON_LABEL = "lexicon_label"

/**
 * A simple [Fragment] subclass.
 * Use the [LexiconFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LexiconViewFragment : Fragment() {
    private var username: String? = null
    private var lexiconLabel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
            lexiconLabel = it.getString(ARG_LEXICON_LABEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lexicon, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param[username] Username identifier
         * @param[lexiconLabel] Lexicon label identifier
         * @return A new instance of fragment LexiconFragment.
         */
        @JvmStatic
        fun newInstance(username: String, lexiconLabel: String) =
                LexiconViewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_USERNAME, username)
                        putString(ARG_LEXICON_LABEL, lexiconLabel)
                    }
                }
    }
}