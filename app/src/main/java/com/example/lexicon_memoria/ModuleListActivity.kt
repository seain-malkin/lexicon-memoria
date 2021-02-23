package com.example.lexicon_memoria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lexicon_memoria.fragments.ModuleListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ModuleListActivity : AppCompatActivity() {

    /** @property username The logged in user */
    private lateinit var username: String

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

        setContentView(R.layout.activity_module_list)

        // Attach the fragment
        supportFragmentManager.let { fm ->
            if (fm.findFragmentById(R.id.module_list_container) == null) {
                fm.beginTransaction().apply {
                    replace(R.id.module_list_container, ModuleListFragment.newInstance())
                    commit()
                }
            }
        }

        // Attach click event for FAB
        findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener { onFabClick() }
    }

    private fun onFabClick() {
        startActivityForResult(DictionarySearchActivity.getIntent(this), REQUEST_DICT_SEARCH)
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