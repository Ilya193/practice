package com.example.studying

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.ItemImageBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val musics = mutableListOf<String>()

    private val adapter = simpleAdapter<String, ItemImageBinding> {
        areItemsSame = { oldItem, newItem ->
            oldItem == newItem
        }

        bind {
            image.load(Uri.parse(it))
            filename.text = it.substringAfterLast("/")
        }
    }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getAllImages()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                        Snackbar.make(
                            binding.root,
                            "i need this permission",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    private fun getAllImages() {
        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.ImageColumns.DATA),
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val data = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                Log.d("attadag", "attadag ${cursor.getString(data)}")
                musics.add(cursor.getString(data))
            }
        }
        adapter.submitList(musics)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.images.adapter = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getAllImages()
            }
            else {
                permissionReadExternalStorage.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getAllImages()
            }
            else {
                permissionReadExternalStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}