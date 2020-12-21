package com.example.lexicon_memoria

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate

/**
 * Allows the creation of a new lexicon
 * @author Seain Malkin (dev@seain.me)
 */
class CreateLexiconActivity : AppCompatActivity() {

    /** UI element to enter lexicon label */
    private lateinit var editLabel: EditText

    /** @see[AppCompatActivity.onCreate] */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lexicon)

        // Creates the top action bar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Use the device night view mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // find UI elements
        editLabel = findViewById(R.id.lexicon_label)
        val buttonCreate = findViewById<Button>(R.id.button_create)

        // Attach event handler for create button
        buttonCreate.setOnClickListener {
            // Create an intent for sending back to the calling activity
            val replyIntent = Intent()

            // If label left empty then cancel result request
            // Otherwise reply with label text
            if (TextUtils.isEmpty(editLabel.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val label = editLabel.text.toString()
                replyIntent.putExtra(EXTRA_LABEL, label)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {

        /** Key for accessing label from intent */
        const val EXTRA_LABEL = "com.example.lexicon_memoria.LABEL"

        /**
         * Returns an intent for launching the activity
         * @param[context] The calling context
         * @return The intent for launching [CreateLexiconActivity]
         */
        fun getIntent(context: Context) : Intent {
            return Intent(context, CreateLexiconActivity::class.java)
        }
    }
}