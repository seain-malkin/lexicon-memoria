package com.example.lexicon_memoria

import com.example.lexicon_memoria.dictionary.merriam_webster.Pronunciation
import org.junit.Test

class PronunciationTest {

    @Test
    fun bix_dir_test() {
        assert(Pronunciation.getDirectory("bixfile.mp3") == "bix")
        assert(Pronunciation.getDirectory("ggfile.mp3") == "gg")
        assert(Pronunciation.getDirectory("_file.mp3") == "number")
        assert(Pronunciation.getDirectory("3file.mp3") == "number")
        assert(Pronunciation.getDirectory("file.mp3") == "f")
        assert(Pronunciation.getDirectory("Gile.mp3") == "G")
    }

    @Test
    fun url_test_1() {
        val file = "3d000001"
        val url = "https://media.merriam-webster.com/audio/prons/en/us/mp3/number/3d000001.mp3"

        assert(Pronunciation.buildAudioUrl(file) == url)
    }

    @Test
    fun url_test_2() {
        val file = "pajama02"
        val url = "https://media.merriam-webster.com/audio/prons/en/us/mp3/p/pajama02.mp3"

        assert(Pronunciation.buildAudioUrl(file) == url)
    }
}