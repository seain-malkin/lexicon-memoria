package com.example.lexicon_memoria.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.databinding.FragmentModuleListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [ModuleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModuleListFragment : Fragment() {

    private lateinit var binding: FragmentModuleListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentModuleListBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ModuleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ModuleListFragment()
    }
}