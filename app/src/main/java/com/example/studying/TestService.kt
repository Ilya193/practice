package com.example.studying

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        println("attadag onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("attadag onStartCommand")
        getSharedPreferences("COUNTER", Context.MODE_PRIVATE).edit()
            .putString("STRING", "stringstring").apply()
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("attadag onDestroy")
    }
}