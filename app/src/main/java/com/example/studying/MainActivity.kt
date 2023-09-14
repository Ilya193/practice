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
        Log.d("dalvik", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("dalvik", "onStart")
        Thread.sleep(2500)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("dalvik", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("dalvik", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("dalvik", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("dalvik", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("dalvik", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("dalvik", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("dalvik", "onRestoreInstanceState")
    }
}