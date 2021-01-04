package com.example.lexicon_memoria.dictionary.merriam_webster

import com.example.lexicon_memoria.dictionary.DictionaryRemoteDataSource.DictionaryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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
    override suspend fun get(@Path("word") word: String) : List<CollegiateResponse>

    companion object {

        /** The API key */
        private const val KEY = "f08d5a23-3852-489f-885b-ebd9134c1d00"

        /** The API base URL */
        private const val URL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"

        /** The [Retrofit] object built with [CollegiateApi] interface */
        val request: CollegiateApi by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
                .create(CollegiateApi::class.java)
        }
    }
}