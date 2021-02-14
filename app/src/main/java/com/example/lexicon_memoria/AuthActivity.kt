package com.example.lexicon_memoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.lexicon_memoria.viewmodel.AuthViewModel
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory

/**
 * Application launcher. Displays a logo and sets up user.
 * Currently, just ensures a user is in database otherwise creates one.
 * Then launches the component list activity. A small delay is enforced for experience.
 */
class AuthActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((application as LexmemApplication).users, this, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authViewModel.user.observe(this, { user ->
            user?.let {
                Log.i("User Found", "${user.id}:${user.username}")
            }
        })
    }
}