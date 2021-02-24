package com.example.lexicon_memoria

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.databinding.ActivityModuleListBinding

class ModuleListActivity : AppCompatActivity() {

    /** @property username The logged in user */
    private lateinit var username: String

    private lateinit var binding: ActivityModuleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the username property from instance state or intent
        username = if (savedInstanceState != null && savedInstanceState[ARG_USERNAME] != null) {
            savedInstanceState[ARG_USERNAME].toString()
        } else if(intent.hasExtra(ARG_USERNAME)) {
            intent.getStringExtra(ARG_USERNAME).toString()
        } else {
            throw IllegalStateException("Username not set")
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = ActivityModuleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the action bar
        setSupportActionBar(binding.toolbar.appToolbar)

        // Attach click event for FAB
        binding.fab.setOnClickListener { onFabClick() }
    }

    private fun onFabClick() {
        startActivityForResult(DictionarySearchActivity.getIntent(this), REQUEST_DICT_SEARCH)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_DICT_SEARCH) {
            data?.let {
                val headwordId = DictionarySearchActivity.getHeadwordId(it)
                Log.i("HeadwordId", "$headwordId")
            }
        }
    }

    companion object {

        private const val ARG_USERNAME = "module_list_activity:username"
        private const val REQUEST_DICT_SEARCH = 0

        /**
         * Creates the intent for launching the activity
         * @param context The application context
         * @param username The logged in username
         * @return The intent object
         */
        fun getIntent(context: Context, username: String): Intent {
            val intent = Intent(context, ModuleListActivity::class.java)
            intent.putExtra(ARG_USERNAME, username)

            return intent
        }
    }
}