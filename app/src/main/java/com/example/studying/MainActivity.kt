package com.example.studying

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.studying.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permissionStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                ContextCompat.startForegroundService(this, Intent(this, MusicService::class.java))
            }
        }

    private val permissionNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                checkSelfPermissionStorage()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionNotifications.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
                else {
                    checkSelfPermissionStorage()
                }
            }
            else {
                checkSelfPermissionStorage()
            }
        }
    }

    private fun checkSelfPermissionStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionStorage.launch(android.Manifest.permission.READ_MEDIA_AUDIO)
            }
            else {
                ContextCompat.startForegroundService(this, Intent(this, MusicService::class.java))
            }
        }
        else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionStorage.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else {
                ContextCompat.startForegroundService(this, Intent(this, MusicService::class.java))
            }
        }
    }
}