package com.example.studying

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("attadag", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("attadag", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("attadag", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("attadag", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("attadag", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("attadag", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("attadag", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("attadag", "onRestoreInstanceState")
    }
}