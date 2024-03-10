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
    private lateinit var notificationManager: NotificationManager

    private val musics = mutableListOf<Uri>()
    private var currentMusic = 0

    override fun onBind(intent: Intent): IBinder = binder

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Audio.AudioColumns.DATA),
            MediaStore.Audio.Media.IS_MUSIC,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                musics.add(Uri.parse(cursor.getString(data)))
            }
        }

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(this, musics[currentMusic])
        mediaSession = MediaSessionCompat(this, "MusicService")

        mediaPlayer.prepare()
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            setPlaybackState(PlaybackStateCompat.STATE_STOPPED, 0)
        }

        val metadata = MediaMetadataCompat.Builder()
            .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.duration.toLong())
            .build()
        mediaSession.setMetadata(metadata)

        setPlaybackState(PlaybackStateCompat.STATE_PLAYING, 0)

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

        val previousButtonIntent = Intent(this, MusicService::class.java)
        previousButtonIntent.action = "ACTION_PREVIOUS"
        val previousButtonPendingIntent =
            PendingIntent.getService(this, 0, previousButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        val nextButtonIntent = Intent(this, MusicService::class.java)
        nextButtonIntent.action = "ACTION_NEXT"
        val nextButtonPendingIntent =
            PendingIntent.getService(this, 0, nextButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        val closeButtonIntent = Intent(this, MusicService::class.java)
        closeButtonIntent.action = "ACTION_CLOSE"
        val closeButtonPendingIntent =
            PendingIntent.getService(this, 0, closeButtonIntent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                mediaPlayer.start()
                setPlaybackState(PlaybackStateCompat.STATE_PLAYING, mediaPlayer.currentPosition)
                val notification = buildNotification()
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.skip_previous, "Previous", previousButtonPendingIntent)
                    .addAction(R.drawable.pause, "Pause", pauseButtonPendingIntent)
                    .addAction(R.drawable.skip_next, "Next", nextButtonPendingIntent)
                    .addAction(R.drawable.ic_close, "Close", closeButtonPendingIntent)
                    .build()
                notificationManager.notify(1, notification)
            }

            override fun onPause() {
                mediaPlayer.pause()
                setPlaybackState(PlaybackStateCompat.STATE_PAUSED, mediaPlayer.currentPosition)
                val notification = buildNotification()
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.skip_previous, "Previous", previousButtonPendingIntent)
                    .addAction(R.drawable.play_arrow, "Play", playButtonPendingIntent)
                    .addAction(R.drawable.skip_next, "Next", nextButtonPendingIntent)
                    .addAction(R.drawable.ic_close, "Close", closeButtonPendingIntent)
                    .build()
                notificationManager.notify(1, notification)
            }

            override fun onSkipToNext() {
                if (currentMusic == musics.size - 1) currentMusic = 0
                else currentMusic++
                onSkip()
            }

            override fun onSkipToPrevious() {
                if (currentMusic == 0) currentMusic = musics.size - 1
                else currentMusic--
                onSkip()
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

        val notification = buildNotification()
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.skip_previous, "Previous", previousButtonPendingIntent)
            .addAction(R.drawable.pause, "Pause", pauseButtonPendingIntent)
            .addAction(R.drawable.skip_next, "Next", nextButtonPendingIntent)
            .addAction(R.drawable.ic_close, "Close", closeButtonPendingIntent)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_PLAY" -> mediaSession.controller.transportControls.play()
            "ACTION_PAUSE" -> mediaSession.controller.transportControls.pause()
            "ACTION_PREVIOUS" -> mediaSession.controller.transportControls.skipToPrevious()
            "ACTION_NEXT" -> mediaSession.controller.transportControls.skipToNext()
            "ACTION_CLOSE" -> mediaSession.controller.transportControls.stop()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaSession.release()
    }

    private fun onSkip() {
        mediaPlayer.stop()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(this@MusicService, musics[currentMusic])
        mediaPlayer.prepare()
        mediaPlayer.start()
        setPlaybackState(PlaybackStateCompat.STATE_PLAYING, mediaPlayer.currentPosition)
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
                            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_STOP

                )
                .build()
        )
    }

    private fun buildNotification(): NotificationCompat.Builder =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Music")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2)
                    .setMediaSession(mediaSession.sessionToken)
            )

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
    }
}