package com.example.studying.presentation

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.ImageItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val images = mutableListOf<String>()
    private val viewModel: MainViewModel by viewModel()
    private val adapter = simpleAdapter<String, ImageItemBinding> {
        bind {
            image.load(it)
        }

        listeners {
            image.onClick {
                viewModel.upload(File(it))
            }
        }
    }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) upload()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.images.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(android.Manifest.permission.READ_MEDIA_IMAGES)
        else
            checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    }

    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            upload()
        } else {
            permissionReadExternalStorage.launch(permission)
        }
    }

    private fun upload() {
        contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.ImageColumns.DATA),
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val filepath = cursor.getString(columnIndex)
                images.add(filepath)
            }
            adapter.submitList(images.reversed())
        }
    }
}