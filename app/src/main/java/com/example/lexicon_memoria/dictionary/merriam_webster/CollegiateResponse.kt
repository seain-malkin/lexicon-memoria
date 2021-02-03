package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApiResult
import com.google.gson.annotations.SerializedName

/**
 * Class used by Retrofit to build service response objects
 * @author Seain Malkin (dev@seain.me)
 */
class CollegiateResponse : DictionaryApiResult {

    override var headword: DictionaryApiResult.Headword
        get() = TODO("Not yet implemented")
        set(value) {}

    override var homographs: List<DictionaryApiResult.Homograph>
        get() = TODO("Not yet implemented")
        set(value) {}
}