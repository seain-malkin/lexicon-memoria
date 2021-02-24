package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.Homograph
import com.example.lexicon_memoria.databinding.ViewCompoundHomographLayoutBinding

/**
 * Custom compound view for displaying a homograph and its definitions
 * @param[homograph] A homograph to display
 * @see[LinearLayout]
 */
class HomographLayout(
    functions: Homograph,
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    init {
        // Define the params
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL

        // Inflate the homograph view
        val binding = ViewCompoundHomographLayoutBinding.inflate(LayoutInflater.from(context), this)

        // Bind the homograph label name
        binding.label.text = "${functions.function.label}"

        // Inflate the view for each definition
        functions.definitions.forEachIndexed { i, d ->
            binding.definitions.addView(DefinitionLayout(i + 1, d.definition, context))
        }
    }

    /**
     * Secondary constructor without attributes
     */
    constructor(
        functions: Homograph,
        context: Context
    ) : this(functions, context, null)
}