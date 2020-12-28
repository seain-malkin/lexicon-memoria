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

        // Attach fragments to activity view
        supportFragmentManager.let { fm ->
            fm.commit {
                fm.findFragmentByTag(TAG_DICTIONARY_SEARCH).let {
                    if (it !is DictionarySearchFragment) {
                        add(R.id.fragment_search,
                            DictionarySearchFragment.newInstance(), TAG_DICTIONARY_SEARCH)
                    }
                }
                fm.findFragmentByTag(TAG_DICTIONARY_RESULT).let {
                    if (it !is DictionaryResultFragment) {
                        add(R.id.fragment_result,
                            DictionaryResultFragment.newInstance(), TAG_DICTIONARY_RESULT)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) : Intent {
            return Intent(context, DictionarySearchActivity::class.java)
        }
    }
}