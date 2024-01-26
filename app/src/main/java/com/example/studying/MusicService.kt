package com.example.studying

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    private val binder = MusicBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaSession: MediaSessionCompat

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun setPlaybackState(state: Int, position: Int) {
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                state,
                position.toLong(),
                1f
            )
                .setActions(
                    PlaybackStateCompat.ACTION_SEEK_TO
                            or PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PAUSE
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_STOP

                )
                .build()
        )
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/engines-1ad8b.appspot.com/o/2jz.MP3?alt=media&token=109a9823-6686-412f-97ba-c1785a72059c")
        mediaSession = MediaSessionCompat(this, "MusicService")

        mediaPlayer.prepare()
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            setPlaybackState(PlaybackStateCompat.STATE_PAUSED, 0)
        }

        val metadata = MediaMetadataCompat.Builder()
            .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.duration.toLong())
            .build()
        mediaSession.setMetadata(metadata)

        setPlaybackState(PlaybackStateCompat.STATE_PLAYING, 0)

        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/engines-1ad8b.appspot.com/o/2jz.MP3?alt=media&token=109a9823-6686-412f-97ba-c1785a72059c")
                mediaPlayer.prepare()
                mediaPlayer.start()
                setPlaybackState(PlaybackStateCompat.STATE_PLAYING, 0)
            }

            override fun onPause() {
                mediaPlayer.pause()
                setPlaybackState(PlaybackStateCompat.STATE_PAUSED, mediaPlayer.currentPosition)
            }

            override fun onSkipToNext() {
            }

            override fun onStop() {
                super.onStop()
                mediaPlayer.release()
                mediaSession.release()
                stopSelf()
            }

            override fun onSeekTo(pos: Long) {
                super.onSeekTo(pos)
                mediaPlayer.seekTo(pos.toInt())
                setPlaybackState(PlaybackStateCompat.STATE_PLAYING, mediaPlayer.currentPosition)
            }
        })

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val playButtonIntent = Intent(this, MusicService::class.java)
        playButtonIntent.action = "ACTION_PLAY"
        val playButtonPendingIntent =
            PendingIntent.getService(this, 0, playButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        val pauseButtonIntent = Intent(this, MusicService::class.java)
        pauseButtonIntent.action = "ACTION_PAUSE"
        val pauseButtonPendingIntent =
            PendingIntent.getService(this, 0, pauseButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        val nextButtonIntent = Intent(this, MusicService::class.java)
        nextButtonIntent.action = "ACTION_NEXT"
        val nextButtonPendingIntent =
            PendingIntent.getService(this, 0, nextButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Music Player")
            .setContentText("Music")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.play_arrow, "Play", playButtonPendingIntent)
            .addAction(R.drawable.pause, "Pause", pauseButtonPendingIntent)
            .addAction(R.drawable.skip_next, "Next", nextButtonPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_PLAY" -> mediaSession.controller.transportControls.play()
            "ACTION_PAUSE" -> mediaSession.controller.transportControls.pause()
            "ACTION_NEXT" -> mediaSession.controller.transportControls.skipToNext()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaSession.release()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
}