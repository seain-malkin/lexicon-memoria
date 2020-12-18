package com.example.lexicon_memoria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.fragments.LexiconListFragment
import com.example.lexicon_memoria.fragments.NewLexiconFragment

private const val TAG_NEW_LEXICON = "new_lexicon"

class NewLexiconActivity : AppCompatActivity() {

    private lateinit var newLexFrag: NewLexiconFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_lexicon)

        // Attach the fragment to the activity
        supportFragmentManager.let { fm ->
            // Check if the fragment already exists
            val fragment = fm.findFragmentByTag(TAG_NEW_LEXICON)

            setSupportActionBar(findViewById(R.id.toolbar))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            // If fragment doesn't exist, create it and attach it
            if (fragment !is NewLexiconFragment) {
                newLexFrag = NewLexiconFragment.newInstance()
                fm.beginTransaction().run {
                    replace(R.id.flContent, newLexFrag, TAG_NEW_LEXICON)
                    commit()
                }
            } else {
                // If already exists, just save the reference
                newLexFrag = fragment
            }
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, NewLexiconActivity::class.java)
        }
    }
}