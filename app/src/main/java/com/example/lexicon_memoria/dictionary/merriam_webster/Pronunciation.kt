package com.example.lexicon_memoria.dictionary.merriam_webster

/**
 * Represents the spoken and audio pronunciation of a dictionary word
 * @property spoken A string that sounds the pronunciation
 * @property audio A URL of an audio recording of the pronunciation
 */
data class Pronunciation(
    val spoken: String,
    var audio: String? = null
) {

    companion object {

        private const val URL = "https://media.merriam-webster.com/audio/prons/en/us/mp3/[dir]/[file].mp3"

        private const val DIR_BIX = "bix"
        private const val DIR_GG = "gg"
        private const val DIR_NUM = "number"
        private const val DIR_ALPHA = "[a-zA-Z]"

        /**
         * Builds the URL for the dictionary audio file
         * @param file The audio file name
         * @return The URL of the audio file
         */
        @JvmStatic
        fun buildAudioUrl(file: String): String {
            return URL.replace("[dir]", getDirectory(file)).replace("[file]", file)
        }

        /**
         * Determines the directory of the audio file location
         * @param file The audio file name
         * @return The directory
         */
        fun getDirectory(file: String): String {
            val matches = Regex("(^$DIR_BIX|^$DIR_GG|^$DIR_ALPHA)").run {
                find(file)
            }

            return matches?.value ?: DIR_NUM
        }
    }
}
