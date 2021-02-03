package com.example.lexicon_memoria.helper

import com.google.gson.GsonBuilder
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 * Builds the Retrofit object. Allows multiple type adapters to be applied to the GSON
 * converter
 */
class RetrofitHelper {

    /**
     * Holds the type reference and it's adapter for converting
     * @property type The type to be converted
     * @property adapter The object that does the converting
     */
    data class TypeAdapter(val type: Type, val adapter: Any)

    companion object {

        /**
         * Creates the Retrofit child class
         * @param url The base url of the API
         * @param typeAdapters A list of type adapters to handle conversion of types
         * @return The child class of Retrofit
         */
        @JvmStatic
        inline fun <reified T>create(url: String, typeAdapters: List<TypeAdapter>? = null): T {
            return Retrofit.Builder()
                .addConverterFactory(createGsonConverterFactory(typeAdapters))
                .baseUrl(url)
                .build()
                .create(T::class.java)
        }

        /**
         * Wrapper for creating the GSON converter factory. Applies the type adapters
         * @param typeAdapters The type adapters to apply
         * @return The factory for use with Retrofit
         */
        @JvmStatic
        fun createGsonConverterFactory(typeAdapters: List<TypeAdapter>?): Converter.Factory {
            val gsonBuilder = GsonBuilder()
            typeAdapters?.forEach { gsonBuilder.registerTypeAdapter(it.type, it.adapter) }
            return GsonConverterFactory.create(gsonBuilder.create())
        }
    }
}