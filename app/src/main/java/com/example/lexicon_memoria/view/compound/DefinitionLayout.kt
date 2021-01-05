package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R

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

    init {
        // Define layout params
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL

        // Inflate the definition view
        val view = inflate(context, R.layout.view_compound_definition_layout, this)

        // Bind the data to the view
        view.findViewById<TextView>(R.id.position_label).text = "$position"
        view.findViewById<TextView>(R.id.definition_text).text = definition
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