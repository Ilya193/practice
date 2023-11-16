package com.example.studying

import android.content.Intent
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

        binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("dalvik@example.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT")
            intent.putExtra(Intent.EXTRA_TEXT, "EXTRA_TEXT")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }
}