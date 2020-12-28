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
import com.example.lexicon_memoria.adapter.LexiconListAdapter.LexiconListAdapterListener
import com.example.lexicon_memoria.database.entity.LexiconEntity
import com.example.lexicon_memoria.fragments.LexiconViewFragment
import com.example.lexicon_memoria.fragments.LexiconListFragment
import com.example.lexicon_memoria.viewmodel.LexiconListViewModel
import com.example.lexicon_memoria.viewmodel.LexiconListViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG_LEXICON_LIST = "lexicon_list"
private const val TAG_LEXICON_VIEW = "lexicon_view"
private const val REQUEST_CODE_CREATE_LEXICON = 1
private const val REQUEST_CODE_DICTIONARY_SEARCH = 2

class LexiconActivity : LexiconListAdapterListener, AppCompatActivity() {

    private lateinit var username: String

    /** The fragment visible to the user */
    private var visibleFragment = TAG_LEXICON_LIST

    /** View Model for displaying list of lexicons */
    private val lexiconListViewModel: LexiconListViewModel by viewModels {
        LexiconListViewModelFactory(
                (application as LexmemApplication).lexicons,
                this,
                intent.extras
        )
    }

    /**
     * Toggles the lexicon list fragment and hides the view fragment
     */
    private fun displayLexiconList() {
        visibleFragment = TAG_LEXICON_LIST

        supportFragmentManager.let { fm ->
            fm.commit {
                setReorderingAllowed(true)
                // Find or create list fragment then display it
                fm.findFragmentByTag(TAG_LEXICON_LIST).let {
                    if (it is LexiconListFragment) {
                        show(it)
                    } else {
                        add(R.id.content, LexiconListFragment.newInstance(username),
                                TAG_LEXICON_LIST)
                    }
                }
                // Hide the view fragment
                fm.findFragmentByTag(TAG_LEXICON_VIEW)?.let {
                    hide(it)
                }
            }
        }
    }

    /**
     * Toggles the lexicon view fragment
     */
    private fun displayLexiconView(lexiconLabel: String) {
        visibleFragment = TAG_LEXICON_VIEW

        supportFragmentManager.let { fm ->
            fm.commit {
                setReorderingAllowed(true)
                // Find or create view fragment and display it
                fm.findFragmentByTag(TAG_LEXICON_VIEW).let {
                    if (it is LexiconViewFragment) {
                        show(it)
                    } else {
                        add(R.id.content, LexiconViewFragment.newInstance(username, lexiconLabel),
                                TAG_LEXICON_VIEW)
                    }
                }
                // Hide the list fragment
                fm.findFragmentByTag(TAG_LEXICON_LIST)?.let {
                    hide(it)
                }
                addToBackStack(null)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view
        setContentView(R.layout.activity_lexicon_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // This will be replaced by login on flash activity
        username = "sjam"
        if (savedInstanceState == null) {
            intent.putExtra("username", username)
            displayLexiconList()
        }

        // Set click event on floating action bar
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            // Decide action to take depending on which fragment is visible to the user
            supportFragmentManager.let { fm ->
                fm.findFragmentByTag(TAG_LEXICON_LIST).let {
                    // If list displayed, create new lexicon
                    if (it is LexiconListFragment && it.isVisible) {
                        startActivityForResult(
                            CreateLexiconActivity.getIntent(this),
                            REQUEST_CODE_CREATE_LEXICON
                        )
                    } else {
                        // If list not displayed, assume lexicon view is. Add new word
                        startActivityForResult(
                            DictionarySearchActivity.getIntent(this),
                            REQUEST_CODE_DICTIONARY_SEARCH
                        )
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CREATE_LEXICON && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(CreateLexiconActivity.EXTRA_LABEL)?.let {
                val lexicon = LexiconEntity("sjam", it, System.currentTimeMillis().toInt())
                lexiconListViewModel.insert(lexicon)
            }
        } else if (requestCode == REQUEST_CODE_DICTIONARY_SEARCH && resultCode == Activity.RESULT_OK) {
            // TODO: Add word
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

    override fun onLexiconListItemClick(label: String) {
        displayLexiconView(label)
    }
}