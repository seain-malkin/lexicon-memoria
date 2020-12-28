package com.example.lexicon_memoria

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import com.example.lexicon_memoria.fragments.DictionaryResultFragment
import com.example.lexicon_memoria.fragments.DictionarySearchFragment

private const val TAG_DICTIONARY_SEARCH = "dictionary_search"
private const val TAG_DICTIONARY_RESULT = "dictionary_result"

/**
 * Dictionary Search Activity
 * @author Seain Malkin (dev@seain.me)
 */
class DictionarySearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dictionary_search)
        setSupportActionBar(findViewById(R.id.toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) : Intent {
            return Intent(context, DictionarySearchActivity::class.java)
        }
    }
}