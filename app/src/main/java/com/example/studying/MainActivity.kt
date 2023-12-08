package com.example.studying

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            ImagesFragment().show(supportFragmentManager, null)
        }

        supportFragmentManager.setFragmentResultListener(
            ImagesFragment.IMAGE_URI_REQUEST,
            this
        ) { _, bundle ->
            binding.image.load(Uri.parse(bundle.getString(ImagesFragment.IMAGE_URI_KEY)))
        }
    }
}