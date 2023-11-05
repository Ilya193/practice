package com.example.studying

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            Snackbar.make(binding.root, "Snackbar", Snackbar.LENGTH_SHORT).show()
        }
    }

    private lateinit var  timer: Timer

    private fun setProperties() {
        binding.cardView.scaleX = 1f
        binding.cardView.scaleY = 1f
        binding.cardView.alpha = 1f
    }

    override fun onResume() {
        super.onResume()
        timer = timer(initialDelay = 10, period = 400L) {
            runOnUiThread {
                binding.cardView.animate().scaleX(2f).scaleY(2f).alpha(0f).apply {
                    duration = 250
                    withEndAction {
                        setProperties()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        setProperties()
    }
}