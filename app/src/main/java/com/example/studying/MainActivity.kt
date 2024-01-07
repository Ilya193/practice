package com.example.studying

import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.studying.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                settings()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {

                    }
                }
            }
        }

    private val permissionReadExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->

        }

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    private fun settings() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera()
        binding.root.setOnClickListener {
            takePhoto()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            settings()
        } else {
            permission.launch(android.Manifest.permission.CAMERA)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionReadExternalStorage.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                imageCapture = ImageCapture.Builder().build()
                val cameraProvider = cameraProviderFuture.get()
                val preview =
                    Preview.Builder().build()
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview, imageCapture
                )
            } catch (e: ExecutionException) {
                println("s149 $e")
            } catch (e: InterruptedException) {
                println("s149 $e")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val photoFile = File(cacheDir, "cacheDir.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    println("s149 $savedUri")

                    val imagesDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val imageFile = File(imagesDir, "Environment.DIRECTORY_PICTURES.jpg")

                    val inputStream = FileInputStream(photoFile)
                    val outputStream = FileOutputStream(imageFile)
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }

                    MediaScannerConnection.scanFile(this@MainActivity, arrayOf(imageFile.absolutePath), null, null)
                }

                override fun onError(exception: ImageCaptureException) {
                    println("s149 ${exception.message}")
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}