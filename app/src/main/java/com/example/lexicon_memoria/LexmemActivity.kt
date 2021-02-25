package com.example.lexicon_memoria

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.databinding.ActivityLexmemBinding
import com.example.lexicon_memoria.fragments.SearchFragment
import com.example.lexicon_memoria.viewmodel.AuthViewModel
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory

class LexmemActivity : SearchFragment.SearchFragmentListener, AppCompatActivity() {

    private lateinit var binding: ActivityLexmemBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((application as LexmemApplication).users, this, intent.extras)
    }

    private val lexmemViewModel: LexmemViewModel by viewModels {
        LexmemViewModelFactory((application as LexmemApplication).userWords, this)
    }

    override fun onAddWord(word: DictionaryWord) {
        supportFragmentManager.let { fm ->
            val fragment = fm.findFragmentById(R.id.content_frame)
            fragment?.let {
                fm.beginTransaction().apply {
                    remove(it)
                    commit()
                }
            }
        }

        Toast.makeText(applicationContext, "${word.headword.label} added", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup night mode, binding, and app bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = ActivityLexmemBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar.appToolbar)

        setContentView(binding.root)

        // Set the user id in view models
        authViewModel.user.observe(this, { user ->
            user?.let { lexmemViewModel.userId = it.id }
        })

        // Check for a search request
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.content_frame, SearchFragment.newInstance(query))
                    commit()
                }
            } ?: throw IllegalStateException("Expected extra not set")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
        }

        return true
    }

    companion object {

        /**
         * Creates the intent for launching the activity
         * @param context The application context
         * @return The intent object
         */
        fun getIntent(context: Context) = Intent(context, LexmemActivity::class.java)
    }
}