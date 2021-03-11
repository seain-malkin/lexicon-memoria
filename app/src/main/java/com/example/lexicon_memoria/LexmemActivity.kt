package com.example.lexicon_memoria

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.lexicon_memoria.database.entity.DictionaryWord
import com.example.lexicon_memoria.databinding.ActivityLexmemBinding
import com.example.lexicon_memoria.fragments.modulelist.ModuleListFragment
import com.example.lexicon_memoria.fragments.modulelist.ModuleListFragment.ModuleListListener
import com.example.lexicon_memoria.fragments.SearchFragment
import com.example.lexicon_memoria.fragments.SearchFragment.SearchFragmentListener
import com.example.lexicon_memoria.fragments.WordListFragment
import com.example.lexicon_memoria.fragments.modulelist.WordListModule
import com.example.lexicon_memoria.viewmodel.UserViewModel
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory
import com.example.lexicon_memoria.viewmodel.LexmemViewModel
import com.example.lexicon_memoria.viewmodel.LexmemViewModelFactory

/**
 * Main activity that displays the module list and search results
 */
class LexmemActivity(

) : ModuleListListener, SearchFragmentListener, AppCompatActivity() {

    private lateinit var binding: ActivityLexmemBinding

    private val userVm: UserViewModel by viewModels {
        AuthViewModelFactory((application as LexmemApplication).users, this, intent.extras)
    }

    private val lexVm: LexmemViewModel by viewModels {
        LexmemViewModelFactory((application as LexmemApplication).userWords, this)
    }

    /**
     * When a module is clicked, load the associated fragment.
     *
     * @see [ModuleListFragment.ModuleListListener.onModuleClick]
     */
    override fun onModuleClick(moduleType: Int, extra: Bundle?) {
        // Decide which fragment to create
        val fragment = when (moduleType) {
            MODULE_WORD_LIST -> WordListModule.fragmentFactory(extra)
            MODULE_INTERVAL -> throw IllegalStateException("Not implemented yet.")
            else -> throw IllegalStateException("An unknown module was requested.")
        }

        // Replace the content frame with the new fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, fragment)
            addToBackStack(null)
            commit()
        }
    }

    /**
     * @see [SearchFragment.SearchFragmentListener.onAddWord]
     */
    override fun onAddWord(word: DictionaryWord) {
        lexVm.addWord(word)

        supportFragmentManager.let { fm ->
            val fragment = fm.findFragmentById(R.id.content_frame)
            fragment?.let {
                fm.beginTransaction().apply {
                    replace(R.id.content_frame, ModuleListFragment.newInstance())
                    commit()
                }
                showToast("${word.headword.label} added")
            }
        }
    }

    /**
     * @see [AppCompatActivity.onCreate]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup night mode, binding, and app bar
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        binding = ActivityLexmemBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar.appToolbar)

        setContentView(binding.root)

        // Set the user id in view models
        userVm.user.observe(this, { user ->
            user?.let { lexVm.setUserId(it.id) }
        })

        // Check for a search request
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.content_frame, SearchFragment.newInstance(query))
                    commit()
                }
            } ?: throw IllegalStateException("Expected extra not set")
        } else { // Display the module list fragment
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.content_frame, ModuleListFragment.newInstance())
                commit()
            }
        }
    }

    /**
     * @see [AppCompatActivity.onCreateOptionsMenu]
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.action_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
        }

        return true
    }

    /**
     * Displays a toast message
     * @param msg The message to display
     */
    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    companion object {

        const val MODULE_WORD_LIST = 1
        const val MODULE_INTERVAL = 2

        /**
         * Creates the intent for launching the activity
         * @param context The application context
         * @return The intent object
         */
        fun getIntent(context: Context) = Intent(context, LexmemActivity::class.java)
    }
}