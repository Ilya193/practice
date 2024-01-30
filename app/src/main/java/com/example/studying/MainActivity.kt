package com.example.studying

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.load
import com.example.studying.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val file = getFileFromUri(uri!!)
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(this, file.toUri())
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

    private lateinit var mediaPlayer: MediaPlayer

    private fun getFileFromUri(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "${UUID.randomUUID()}.mp3")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageLoader = ImageLoader.Builder(this)
            .components {
                add(GifDecoder.Factory())
            }
            .build()

        binding.image.load("https://automotive-heritage.com/upload/a1540491372.gif", imageLoader)

        //Glide.with(this).asGif().load("https://automotive-heritage.com/upload/a1540491372.gif").into(binding.image)

        binding.root.setOnClickListener {
            val mimeType = arrayOf("audio/mp4", "audio/mp3", "audio/mpeg")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            pickMedia.launch("audio/*")
        }
    }
}