package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lexicon_memoria.LexmemApplication
import com.example.lexicon_memoria.adapter.WordListAdapter
import com.example.lexicon_memoria.databinding.FragmentWordListBinding
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [WordListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordListFragment : Fragment() {

    private val vm: LexmemViewModel by activityViewModels {
        LexmemViewModelFactory((requireActivity().application as LexmemApplication).userWords, this)
    }

    private lateinit var binding: FragmentWordListBinding
    private val adapter = WordListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWordListBinding.inflate(layoutInflater, container, false)

        binding.listContainer.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WordListFragment.
         */
        @JvmStatic
        fun newInstance() = WordListFragment()
    }
}