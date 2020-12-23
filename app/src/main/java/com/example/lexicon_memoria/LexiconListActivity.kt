package com.example.lexicon_memoria

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.fragments.LexiconListFragment
import com.example.lexicon_memoria.viewmodel.LexiconListViewModel
import com.example.lexicon_memoria.viewmodel.LexiconListViewModelFactory

private const val TAG_LEXICON_LIST = "lexicon_list"
private const val REQUEST_CODE_CREATE_LEXICON = 1

class LexiconListActivity : AppCompatActivity() {

    /** Reference to the [LexiconListFragment] */
    private lateinit var lexListFrag: LexiconListFragment

    /** View Model for displaying list of lexicons */
    private val lexiconListViewModel: LexiconListViewModel by viewModels {
        LexiconListViewModelFactory(
                (application as LexmemApplication).lexicons,
                this,
                intent.extras
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexicon_list)

        if (savedInstanceState == null) {
            intent.putExtra("username", "sjam")
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Attach the fragment to the activity
        supportFragmentManager.let { fm ->
            // Check if the fragment already exists
            val fragment = fm.findFragmentByTag(TAG_LEXICON_LIST)

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

        fab.setOnClickListener {
            startActivityForResult(
                CreateLexiconActivity.getIntent(this),
                REQUEST_CODE_CREATE_LEXICON
            )
        }

        // Define an observer on the lexicon list
        lexiconListViewModel.all.observe(this, Observer { lexicons ->
            lexicons?.let { lexListFrag.adapter?.submitList(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CREATE_LEXICON && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(CreateLexiconActivity.EXTRA_LABEL)?.let {
                val lexicon = LexiconEntity("sjam", it, System.currentTimeMillis().toInt())
                lexiconListViewModel.insert(lexicon)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lexicon_list, menu)
        return super.onCreateOptionsMenu(menu)
    }
}