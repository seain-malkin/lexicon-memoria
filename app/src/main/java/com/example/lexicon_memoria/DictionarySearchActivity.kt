package com.example.lexicon_memoria

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.lexicon_memoria.databinding.ActivityDictionarySearchBinding
import com.example.lexicon_memoria.fragments.DictionaryResultFragment.DictionaryResultListener

/**
 * Dictionary Search Activity
 * @author Seain Malkin (dev@seain.me)
 */
class DictionarySearchActivity : DictionaryResultListener, AppCompatActivity() {

    private lateinit var binding: ActivityDictionarySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDictionarySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.appToolbar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    override fun onSaveWord(headwordId: Long) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra(ARG_HEADWORD_ID, headwordId) })
        finish()
    }

    companion object {

        const val ARG_HEADWORD_ID = "dictionary_search_activity:headword_id"

        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, DictionarySearchActivity::class.java)
        }

        /**
         * Returns the intent extra representing the headword id
         * @param intent The intent to retrieve from
         * @return The headword id
         */
        fun getHeadwordId(intent: Intent): Long {
            return intent.getLongExtra(ARG_HEADWORD_ID, 0L)
        }
    }
}