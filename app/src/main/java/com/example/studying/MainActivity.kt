package com.example.studying

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.studying.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val workRequest = PeriodicWorkRequest.Builder(TestWorker::class.java, 1, TimeUnit.DAYS)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun calculateInitialDelay(): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 2)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val currentTime = System.currentTimeMillis()
        var initialDelay: Long = calendar.timeInMillis - currentTime
        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toMillis(1)
        }
        return initialDelay
    }
}