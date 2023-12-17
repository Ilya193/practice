package com.example.studying.presentation

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.load
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.ImageItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.Timer
import java.util.TimerTask
import java.util.UUID


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var index = 0
    private val images = mutableListOf<ItemUi>()
    private val viewModel: MainViewModel by viewModel()
    private val adapter = adapter {
        addBinding<ItemUi, ImageItemBinding> {
            areItemsSame = { oldItem, newItem ->
                oldItem.id == newItem.id
            }

            areContentsSame = { oldItem, newItem ->
                oldItem == newItem
            }

            changePayload = { oldItem, newItem ->
                oldItem.uploading != newItem.uploading
            }

            bindWithPayloads { item, payloads ->
                println("attadag $item $payloads")
                if (payloads.isNotEmpty()) {
                    val result = payloads[0] as Boolean
                    if (result) status.text = if (item.uploading) "Файл загружается" else ""
                } else {
                    status.text = if (item.uploading) "Файл загружается" else ""
                    image.load(item.path)
                    status.text = ""
                }
            }

            listeners {
                image.onClick { item ->
                    images.find {
                        it == item
                    }?.let {
                        val index = images.indexOf(it)
                        images[index] = it.copy(uploading = !it.uploading)
                        submitList()
                        viewModel.upload(File(item.path), images[index].id)
                    }
                }
            }
        }
    }

    private fun submitList() {
        adapter.submitList(images.reversed())
    }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) photoSearch()
        }

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            viewModel.upload(file, -1)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    file.delete()
                }
            }, 5000)
        }
    }

    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.takePicture.setOnClickListener {
            file = File(filesDir, "${UUID.randomUUID()}.jpg")
            val uri =
                FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
            takePicture.launch(uri)
        }

        binding.images.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(android.Manifest.permission.READ_MEDIA_IMAGES)
        else
            checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        viewModel.upload.observe(this) {
            it.getContentOrNot { id ->
                images.find { 
                    it.id == id
                }?.let { item ->
                    val index = images.indexOf(item)
                    images[index] = item.copy(uploading = !item.uploading)
                    submitList()
                }
            }
        }
    }

    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            photoSearch()
        } else {
            permissionReadExternalStorage.launch(permission)
        }
    }

    private fun photoSearch() {
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
                images.add(ItemUi(id = index++, path = filepath))
            }
            adapter.submitList(images.reversed())
        }
    }
}