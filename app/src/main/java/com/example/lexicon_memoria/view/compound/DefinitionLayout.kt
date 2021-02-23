package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.databinding.ViewCompoundDefinitionLayoutBinding

/**
 * Displays the definition belonging to a homograph
 * @param[position] The position from the definition list
 * @param[definition] The definition to display
 * @see[LinearLayout]
 */
class DefinitionLayout(
    private val position: Int,
    private val definition: String,
    context: Context,
    attributeSet: AttributeSet?
) : LinearLayout(context, attributeSet) {

    private val binding: ViewCompoundDefinitionLayoutBinding

    init {
        // Define layout params
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL

        // Inflate the definition view
        binding = ViewCompoundDefinitionLayoutBinding.inflate(LayoutInflater.from(context), this)

        // Bind the data to the view
        binding.positionLabel.text = "$position"
        binding.definitionText.text = definition
    }

    /**
     * Secondary constructor without attributes
     */
    constructor(
        position: Int,
        definition: String,
        context: Context
    ) : this(position, definition, context, null)
}