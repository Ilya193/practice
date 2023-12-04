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
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import com.elveum.elementadapter.simpleAdapter
import com.example.studying.databinding.ActivityMainBinding
import com.example.studying.databinding.EmptyItemBinding
import com.example.studying.databinding.ItemImageBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val musics = mutableListOf<Image>()

    private val adapter = adapter<Image> {
        addBinding<Image.Success, ItemImageBinding> {
            bind {
                image.load(Uri.parse(it.path))
                filename.text = it.path.substringAfterLast("/")
            }
        }

        addBinding<Image.NotFound, EmptyItemBinding> {
            listeners {
                btnRetry.onClick {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
                    else
                        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
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
                musics.add(Image.Success(cursor.getString(data)))
            }
        }
        if (musics.isEmpty())
            adapter.submitList(listOf(Image.NotFound))
        else
            adapter.submitList(musics)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
        else
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.images.adapter = adapter
    }

    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getAllImages()
        }
        else {
            permissionReadExternalStorage.launch(permission)
        }
    }
}