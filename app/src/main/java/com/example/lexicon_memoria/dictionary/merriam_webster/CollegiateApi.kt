package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApi
import com.example.lexicon_memoria.helper.RetrofitHelper
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.OPTIONS
import retrofit2.http.Path
import java.lang.reflect.Type

/**
 * Provides access to the Merriam Webster dictionary api
 */
interface CollegiateApi : DictionaryApi {

    /**
     * Search the dictionary for a word. Multiple results may be returned
     * @param[word] The key word to search
     * @return A list of results
     */
    @GET("{word}?key=$KEY")
    override fun find(@Path("word") key: String): CollegiateResponse

    companion object {

        /** @property name Identifies the name of the api source */
        const val name = "collegiate"

        /** The API key */
        private const val KEY = "f08d5a23-3852-489f-885b-ebd9134c1d00"

        /** The API base URL */
        private const val URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"

        /** @property source The built [Retrofit] object */
        val source: CollegiateApi by lazy {
            RetrofitHelper.create(URL, listOf(RetrofitHelper.TypeAdapter(
                CollegiateResponse::class.java,
                CollegiateResponseDeserializer()
            )))
        }
    }
}