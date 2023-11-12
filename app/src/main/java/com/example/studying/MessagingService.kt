package com.example.studying

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("attadag", "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title
        val body = message.notification?.body
        Log.d("attadag", "onMessageReceived: $title $body")
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("MESSAGE").apply {
            putExtra("TITLE", title)
            putExtra("BODY", body)
        })
    }
}