package com.example.studying

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import coil.load
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.EmptyItemBinding
import com.example.studying.databinding.ItemImageBinding
import com.google.android.material.snackbar.Snackbar

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