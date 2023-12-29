package com.example.studying

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.Executors

class BoundService : Service() {

    private var block: ((Int) -> Unit)? = null
    override fun onBind(intent: Intent?): IBinder? = LocalBinder()

    override fun onUnbind(intent: Intent?): Boolean {
        this@BoundService.block =  null
        return false
    }

    inner class LocalBinder : Binder() {
        fun subscribe(block: (Int) -> Unit) {
            this@BoundService.block = block
            Executors.newSingleThreadExecutor().execute {
                for (i in 0..100) {
                    Thread.sleep(250)
                    this@BoundService.block?.invoke(i)
                }
            }
        }

        fun unsubscribe() {
            this@BoundService.block =  null
        }
    }
}