package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.dictionary.Word

/**
 * Custom layout for displaying a word along with its homographs
 * @see[LinearLayout]
 */
class WordLayout(
    context: Context,
    attr: AttributeSet?
) : LinearLayout(context, attr) {

    /** @property[word] The word object that the view binds to */
    var word: Word? = null
        set(value) {
            // Only update if the word is different to current value
            if (!(field != null && field!! == value)) {
                field = value
                updateView()
            }
        }

    /**
     * Secondary constructor if attrs not present
     * @param[context] The view context
     */
    constructor(context: Context) : this(context, null)

    /**
     * Displays the custom view inside the linear layout
     */
    private fun updateView() {
        // First delete any current child views
        removeAllViews()

        // Inflate the layout view
        val view = inflate(context, R.layout.view_compound_word_layout, this)

        // Find and update view elements
        view.findViewById<TextView>(R.id.headword).apply {
            text = "$word"
        }

        // Force the view to redraw by invalidating it
        invalidate()
    }
}