package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.dictionary.Homograph

/**
 * Custom compound view for displaying a homograph and its definitions
 * @param[homograph] A homograph to display
 * @see[LinearLayout]
 */
class HomographLayout(
    private val homograph: Homograph,
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    init {
        // Define the params
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL

        // Inflate the homograph view
        val view = inflate(context, R.layout.view_compound_homograph_layout, this)

        // Bind the homograph label name
        view.findViewById<TextView>(R.id.label).text = "$homograph"

        // Find the container for displaying the definitions
        val llDefinitions: LinearLayout = view.findViewById(R.id.definitions)

        // Inflate the view for each definition
        homograph.definitions.forEachIndexed { index, definition ->
            llDefinitions.addView(DefinitionLayout(index + 1, definition, context))
        }
    }

    /**
     * Secondary constructor without attributes
     */
    constructor(
        homograph: Homograph,
        context: Context
    ) : this(homograph, context, null)
}