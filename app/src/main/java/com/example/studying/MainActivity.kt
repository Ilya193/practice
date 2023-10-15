package com.example.studying

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.studying.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val request = OneTimeWorkRequest.Builder(TestWorker::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(Constraints.Builder().setRequiresCharging(false).build())
            .build()

        WorkManager.getInstance(this).enqueue(request)

        binding.tvName.setOnClickListener {
            val name = getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getInt("number", -1)
            binding.tvName.text = name.toString()
        }
    }
}