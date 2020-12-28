package com.example.lexicon_memoria.dictionary

class GoogleDictionaryApi(

) : DictionaryRemoteDataSource.DictionaryApi {

    override suspend fun requestWord(word: String, language: String?) : DictionaryWord {
        return DictionaryWord(word)
    }
}