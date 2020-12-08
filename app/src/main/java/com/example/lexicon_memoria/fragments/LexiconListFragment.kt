package com.example.lexicon_memoria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lexicon_memoria.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LexiconListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LexiconListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lexicon_list, container, false)

        // Setup the recycler view to display the lexicon list
        rv = view.findViewById(R.id.rvLexList)
        rv.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rv.adapter = LexListAdapter()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LexiconListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
            LexiconListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private inner class LexListAdapter : RecyclerView.Adapter<LexListVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LexListVH {
            return LexListVH(LayoutInflater.from(activity), parent)
        }

        override fun onBindViewHolder(holder: LexListVH, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return 25
        }
    }

    /**
     * Represents each view in the lexicon list
     */
    inner class LexListVH(
        li: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        li.inflate(R.layout.lexicon_list_view, parent, false)
    ) {
        fun bind(position: Int) {

        }
    }
}