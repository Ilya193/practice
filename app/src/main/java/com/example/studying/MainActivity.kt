package com.example.studying

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.studying.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->

        }

    private val takePictureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imagesDir, "${UUID.randomUUID()}.jpg")

            val inputStream = contentResolver.openInputStream(tempFileUri)
            val outputStream = FileOutputStream(imageFile)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            MediaScannerConnection.scanFile(this, arrayOf(imageFile.absolutePath), null, null)
            tempFile.delete()
        }

    private lateinit var tempFileUri: Uri
    private lateinit var tempFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            tempFile = File.createTempFile(
                "temp_image",
                ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            tempFileUri = FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".provider",
                tempFile
            )
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri)
            takePictureLauncher.launch(takePictureIntent)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionReadExternalStorage.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}