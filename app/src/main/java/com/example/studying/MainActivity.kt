package com.example.studying

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val localReceiver = LocalBroadcastReceiver()
    private val systemReceiver = SystemBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.textView.setOnClickListener {
            val intent = Intent("com.example.studying")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        // system
        registerReceiver(systemReceiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
        // local
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(localReceiver, IntentFilter("com.example.studying"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver)
        unregisterReceiver(systemReceiver)
    }
}