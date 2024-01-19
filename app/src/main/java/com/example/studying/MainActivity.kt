package com.example.studying

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.studying.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            ContextCompat.startForegroundService(this, Intent(this, MusicService::class.java))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                ContextCompat.startForegroundService(this, Intent(this, MusicService::class.java))
            }
        }
    }
}