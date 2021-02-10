package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.Homograph

/**
 * Custom compound view for displaying a homograph and its definitions
 * @param[homograph] A homograph to display
 * @see[LinearLayout]
 */
class HomographLayout(
    private val functions: Homograph,
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
        view.findViewById<TextView>(R.id.label).text = "${functions.function.name}"

        // Find the container for displaying the definitions
        val llDefinitions: LinearLayout = view.findViewById(R.id.definitions)

        // Inflate the view for each definition
        functions.definitions.forEachIndexed { i, d ->
            llDefinitions.addView(DefinitionLayout(i + 1, d.definition, context))
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