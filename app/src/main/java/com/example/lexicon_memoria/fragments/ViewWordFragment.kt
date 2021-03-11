package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lexicon_memoria.R

/**
 * A simple [Fragment] subclass.
 * Use the [ViewWordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewWordFragment : Fragment() {
    private var wordId: Long? = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            wordId = it.getLong(ARG_WORD_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_word, container, false)
    }

    companion object {
        private const val ARG_WORD_ID = "view_word_fragment:word_id"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param wordId Parameter 1.
         * @return A new instance of fragment ViewWordFragment.
         */
        @JvmStatic
        fun newInstance(wordId: Long) =
            ViewWordFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_WORD_ID, wordId)
                }
            }
    }
}