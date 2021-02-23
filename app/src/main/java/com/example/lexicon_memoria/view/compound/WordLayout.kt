package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lexicon_memoria.R
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.databinding.ViewCompoundWordLayoutBinding

/**
 * Custom layout for displaying a word along with its homographs
 * @see[LinearLayout]
 */
class WordLayout(
    context: Context,
    attr: AttributeSet?
) : LinearLayout(context, attr) {

    /**
     * Secondary constructor if attrs not present
     * @param[context] The view context
     */
    constructor(context: Context) : this(context, null)

    /** @property[word] The word object that the view binds to */
    var word: DictionaryWord? = null
        set(value) {
            if (!(field != null && field!! == value)) {
                field = value
                updateView(value!!)
            }
        }

    /**
     * Displays the custom view inside the linear layout
     */
    private fun updateView(wordItem: DictionaryWord) {
        // First delete any current child views
        removeAllViews()

        // Inflate the layout view
        val binding = ViewCompoundWordLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        // Bind the headword
        binding.headword.text = "${wordItem.headword.name}"

        // Attach each homograph to the container
        wordItem.functions.forEach {
            binding.homographs.addView(HomographLayout(it, context))
        }

        // Force the view to redraw by invalidating it
        invalidate()
    }

    fun showPlaceholder() {
        removeAllViews()
        invalidate()
    }
}