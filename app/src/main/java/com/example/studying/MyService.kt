package com.example.studying

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private val mediaPlayer = MediaPlayer()
    private val musics = mutableListOf<Uri>()

    private var currentMusic = 0

    override fun onCreate() {
        super.onCreate()
        Log.d("attadag", "onCreate")

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Audio.AudioColumns.DATA),
            MediaStore.Audio.Media.IS_MUSIC,
            null,
            null
        )?.use { cursor ->
            var i = 0
            while (cursor.moveToNext()) {
                val data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                //Log.d("attadag", "attadag ${cursor.getString(data)}")
                musics.add(Uri.parse(cursor.getString(data)))
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Log.d("attadag", "$intent")
        Log.d("attadag", "$musics")

        val action = intent?.getStringExtra("ACTION") ?: ""

        Log.d("attadag", "$action")

        when (action) {
            "stop" -> {
                stopSelf()
            }
            "skipPrevious" -> {
                if (currentMusic == 0) currentMusic = musics.size - 1
                else currentMusic--

                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(this, musics[currentMusic])
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
            "skipNext" -> {
                if (currentMusic == musics.size - 1) currentMusic = 0
                else currentMusic++

                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(this, musics[currentMusic])
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
            else -> {
                val skipPrevious = Intent(this, MyService::class.java).apply {
                    putExtra("ACTION", "skipPrevious")
                }

                val stop = Intent(this, MyService::class.java).apply {
                    putExtra("ACTION", "stop")
                }

                val skipNext = Intent(this, MyService::class.java).apply {
                    putExtra("ACTION", "skipNext")
                }

                val skipPreviousPendingIntent = PendingIntent.getService(this, 1, skipPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
                val stopPendingIntent = PendingIntent.getService(this, 2, stop, PendingIntent.FLAG_UPDATE_CURRENT)
                val skipNextPendingIntent = PendingIntent.getService(this, 3, skipNext, PendingIntent.FLAG_UPDATE_CURRENT)

                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Test title")

                    .setContentText("Test text")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .addAction(R.drawable.ic_skip_previous, "Skip previous", skipPreviousPendingIntent)
                    .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
                    .addAction(R.drawable.ic_skip_next, "Skip next", skipNextPendingIntent)


                startForeground(1, notification.build())

                mediaPlayer.setDataSource(this, musics[currentMusic])
                mediaPlayer.prepare()
                mediaPlayer.setOnCompletionListener {
                    it.stop()
                    it.reset()
                    it.setDataSource(this, musics[++currentMusic])
                    it.prepare()
                    it.start()
                }
                mediaPlayer.start()
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("attadag", "onDestroy")
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    companion object {
        const val CHANNEL_ID = "1"
        const val CHANNEL_NAME = "CHANNEL_NAME"
    }
}