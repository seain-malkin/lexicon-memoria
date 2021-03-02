package com.example.lexicon_memoria.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class AudioService : Service(), MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uri = intent?.getStringExtra(ARG_URI) ?: throw IllegalStateException("URI not set.")

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(uri)
            setOnPreparedListener(this@AudioService)
            prepareAsync()
        }

        return START_STICKY
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.i("AudioService", "$what $extra")

        return false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        mediaPlayer?.apply {
            stop()
            release()
        }

        mediaPlayer = null
    }

    companion object {

        private const val ARG_URI = "audio_service:uri"

        @JvmStatic
        fun getIntent(uri: String, context: Context): Intent {
            return Intent(context, AudioService::class.java).apply {
                putExtra(ARG_URI, uri)
            }
        }
    }
}