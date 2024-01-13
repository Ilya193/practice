package com.example.studying

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding.root.setOnClickListener {
            val text = binding.textView.text.toString()
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text))
        }
    }
}