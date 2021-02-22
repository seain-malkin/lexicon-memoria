package com.example.lexicon_memoria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

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

        Log.i("User", "$username")

        setContentView(R.layout.activity_module_list)
    }

    companion object {

        private const val ARG_USERNAME = "module_list_activity:username"

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