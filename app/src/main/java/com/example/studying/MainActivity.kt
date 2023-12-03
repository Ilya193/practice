package com.example.studying

import android.content.Intent
import android.net.Uri
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

        val geoString = "geo:";
        val geoUri = Uri.parse(geoString);
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri);

        binding.root.setOnClickListener {
            startActivity(mapIntent)
        }
    }
}