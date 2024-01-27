package com.example.studying

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.load
import com.example.studying.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) binding.image.load(uri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageLoader = ImageLoader.Builder(this)
            .build()

        binding.image.load("") {
            listener(onStart = {
                binding.loading.visibility = View.VISIBLE
            }, onSuccess = { _, _ ->
                binding.loading.visibility = View.GONE
            }, onError = { _, _ ->
                binding.loading.visibility = View.GONE
            })
        }

        //Glide.with(this).asGif().load("https://automotive-heritage.com/upload/a1540491372.gif").into(binding.image)

        binding.root.setOnClickListener {
            //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }
}