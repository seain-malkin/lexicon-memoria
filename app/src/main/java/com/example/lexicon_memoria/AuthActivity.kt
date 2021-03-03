package com.example.lexicon_memoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.lexicon_memoria.databinding.ActivityAuthBinding
import com.example.lexicon_memoria.viewmodel.UserViewModel
import com.example.lexicon_memoria.viewmodel.AuthViewModelFactory

/**
 * Application launcher. Displays a logo and sets up user.
 * Currently, just ensures a user is in database otherwise creates one.
 * Then launches the component list activity. A small delay is enforced for experience.
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val userViewModel: UserViewModel by viewModels {
        AuthViewModelFactory((application as LexmemApplication).users, this, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.user.observe(this, { user ->
            user?.let {
                startActivity(LexmemActivity.getIntent(this))
                finish()
            }
        })
    }
}