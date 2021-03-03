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
        val uri = intent?.getStringExtra(ARG_DATA) ?: throw IllegalStateException("URI not set.")
        val action = intent.getIntExtra(ARG_ACTION, 0)

        if (ACTION_PLAY == action) {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(uri)
                setOnPreparedListener(this@AudioService)
                setOnErrorListener(this@AudioService)
                prepareAsync()
            }
        }
        Log.i("Service", "$action")

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


        private const val ARG_ACTION = "audio_service:arg_action"
        private const val ARG_DATA = "audio_service:arg_data"
        const val ACTION_PLAY = 1

        @JvmStatic
        fun getIntent(context: Context, uri: String, action: Int): Intent {
            return Intent(context, AudioService::class.java).apply {
                putExtra(ARG_ACTION, action)
                putExtra(ARG_DATA, uri)
            }
        }
    }
}