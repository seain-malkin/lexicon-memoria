package com.example.lexicon_memoria

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.fragments.LexiconListFragment
import com.example.lexicon_memoria.viewmodel.LexiconListViewModel
import com.example.lexicon_memoria.viewmodel.LexiconListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG_LEXICON_LIST = "lexicon_list"
private const val REQUEST_CODE_CREATE_LEXICON = 1

class LexiconActivity : AppCompatActivity() {

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

        // Set up view
        setContentView(R.layout.activity_lexicon_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // This will be replaced by login on flash activity
        val username = "sjam"
        if (savedInstanceState == null) {
            intent.putExtra("username", username)
        }

        // Attach the list fragment
        supportFragmentManager.run {
            findFragmentByTag(TAG_LEXICON_LIST) ?: commit {
                replace(R.id.content, LexiconListFragment.newInstance(username), TAG_LEXICON_LIST)
                setReorderingAllowed(true)
            }
        }

        // Set click event on floating action bar
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivityForResult(
                CreateLexiconActivity.getIntent(this),
                REQUEST_CODE_CREATE_LEXICON
            )
        }
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