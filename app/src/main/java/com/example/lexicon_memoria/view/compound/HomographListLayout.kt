package com.example.lexicon_memoria.view.compound

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Custom compound view for displaying the homographs and their definitons
 * @see[LinearLayout]
 */
class HomographListLayout(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    /**
     * Secondary constructor when attributes are not present
     * @param[context] The context to pass to the primary constructor
     */
    constructor(context: Context) : this(context, null)
}