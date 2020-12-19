package com.example.lexicon_memoria.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.lexicon_memoria.R

class NewLexiconDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_create_lexicon, null))
                    .setPositiveButton(R.string.dialog_create,
                        DialogInterface.OnClickListener { dialog, id ->
                            // do pos
                        })
                    .setNegativeButton(R.string.dialog_cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                        // do neg
                        })
                    .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}