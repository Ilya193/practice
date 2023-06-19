package com.example.studying

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class LocalBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("attadag", "local")
    }
}