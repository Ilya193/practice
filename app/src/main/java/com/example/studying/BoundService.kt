package com.example.studying

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundService : Service() {

    private var blockStart: (() -> Unit)? = null
    private var blockStop: (() -> Unit)? = null
    override fun onBind(intent: Intent?): IBinder? = LocalBinder()

    override fun onUnbind(intent: Intent?): Boolean {
        this@BoundService.blockStart = null
        this@BoundService.blockStop = null
        return false
    }

    inner class LocalBinder : Binder() {
        fun subscribe(block: () -> Unit) {
            this@BoundService.blockStart = block
        }

        fun unsubscribe(block: () -> Unit) {
            this@BoundService.blockStop = block
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(ACTION)) {
            START_SERVICE -> blockStart?.invoke()
            STOP_SERVICE -> {
                blockStop?.invoke()
                this@BoundService.blockStart = null
                this@BoundService.blockStop = null
            }
        }

        return START_NOT_STICKY
    }

    companion object {
        const val ACTION = "ACTION"
        const val START_SERVICE = "START_SERVICE"
        const val STOP_SERVICE = "STOP_SERVICE"
    }
}