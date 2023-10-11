package com.example.studying

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.Executors

class BoundService : Service() {

    private var block: ((Int) -> Unit)? = null
    override fun onBind(intent: Intent?): IBinder? = LocalBinder()

    inner class LocalBinder : Binder() {
        fun subscribe(block: (Int) -> Unit) {
            this@BoundService.block = block
        }

        fun unsubscribe() {
            this@BoundService.block =  null
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Executors.newSingleThreadExecutor().execute {
            for (i in 0..100) {
                Thread.sleep(150)
                block?.invoke(i)
            }

            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("attadag onDestroy")
    }
}