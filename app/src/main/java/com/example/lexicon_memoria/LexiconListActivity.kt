package com.example.lexicon_memoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.fragments.LexiconListFragment
import com.example.lexicon_memoria.fragments.NewLexiconDialogFragment

private const val TAG_LEXICON_LIST = "lexicon_list"
private const val TAG_DIALOG_NEW_LEXICON = "dialog_new_lexicon"

class LexiconListActivity : AppCompatActivity() {

    /** Reference to the [LexiconListFragment] */
    private lateinit var lexListFrag: LexiconListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexicon_list)

        // Attach the fragment to the activity
        supportFragmentManager.let { fm ->
            // Check if the fragment already exists
            val fragment = fm.findFragmentByTag(TAG_LEXICON_LIST)

            setSupportActionBar(findViewById(R.id.toolbar))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            // If fragment doesn't exist, create it and attach it
            if (fragment !is LexiconListFragment) {
                lexListFrag = LexiconListFragment.newInstance()
                fm.beginTransaction().run {
                    replace(R.id.flContent, lexListFrag, TAG_LEXICON_LIST)
                    commit()
                }
            } else {
                // If already exists, just save the reference
                lexListFrag = fragment
            }
        }

        // Floating Action Button
        val fab: View = findViewById(R.id.fab)
        // On click, prompt for a new lexicon
        val newLexiconDialog = NewLexiconDialogFragment()

        fab.setOnClickListener {
            newLexiconDialog.show(supportFragmentManager, TAG_DIALOG_NEW_LEXICON)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lexicon_list, menu)
        return super.onCreateOptionsMenu(menu)
    }
}