package com.example.studying

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (!result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Snackbar.make(
                            binding.root,
                            "i need this permission",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*binding.root.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(this, MyService::class.java))
        }

        binding.textView.setOnClickListener {
            val stop = Intent(this, MyService::class.java).apply {
                putExtra("ACTION", "stop")
            }
            stopService(stop)
        }*/

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionReadExternalStorage.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}