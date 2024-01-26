package com.example.studying

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class MainActivity() : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val value = sharedPreferences.getInt("random", -1)

        if (value != -1) Snackbar.make(binding.root, "$value", Snackbar.LENGTH_SHORT).show()
        else {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(this, 0,
                Intent(this, CustomReceiver::class.java), PendingIntent.FLAG_MUTABLE)

            val triggerTime = SystemClock.elapsedRealtime() + 10 * 1000

            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
        }
    }
}

class CustomReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.getSharedPreferences("data", Context.MODE_PRIVATE)?.edit()?.putInt("random", Random.nextInt())?.apply()
    }

}