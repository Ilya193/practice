package com.example.studying

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TestService::class.java)
        val pendingIntent =
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val timeInMilliseconds: Long = 60000
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            timeInMilliseconds,
            pendingIntent
        )

        val data = getSharedPreferences("COUNTER", Context.MODE_PRIVATE).getString("STRING", "") ?: ""
        if (data.isNotEmpty())
            binding.title.text = data
    }
}