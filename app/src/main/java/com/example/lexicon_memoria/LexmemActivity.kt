package com.example.lexicon_memoria

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.databinding.ActivityLexmemBinding
import com.example.lexicon_memoria.viewmodel.AuthViewModel
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory

class LexmemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLexmemBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((application as LexmemApplication).users, this, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup night mode, binding, and app bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = ActivityLexmemBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar.appToolbar)

        setContentView(binding.root)

        // Check for a search request
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Log.i("Query", "$query")
            }
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