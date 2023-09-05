package com.example.studying.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.R
import com.example.studying.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel.fetchPosts()

        findViewById<TextView>(R.id.tvHello).setOnClickListener {
            mainViewModel.fetchPosts()
        }

        mainViewModel.success.observe(this) {
            Log.w("attadag", it.toString())
        }

        mainViewModel.error.observe(this) {
            it.getContentOrNot {
                Log.w("attadag", it.toString())
            }
        }
    }
}