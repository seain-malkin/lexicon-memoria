package com.example.lexicon_memoria.dictionary

class GoogleDictionaryApi(

) : DictionaryRemoteDataSource.DictionaryApi {

    override suspend fun lookup(word: String, language: String?): List<DictionaryWord> {
        return listOf(DictionaryWord(word))
    }
}

/**
 * Language codes used in the google api
 * @param[code] The value of the api code
 */
enum class Language(val code: String) {
    ENGLISH("en"),
    HINDI("hi"),
    SPANISH("es"),
    FRENCH("fr"),
    JAPANESE("ja"),
    RUSSIAN("ru"),
    GERMAN("de"),
    ITALIAN("it"),
    KOREAN("ko"),
    BRAZILIAN_PORTUGUESE("pt-BR"),
    ARABIC("ar"),
    TURKISH("tr")
}