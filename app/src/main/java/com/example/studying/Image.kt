package com.example.studying

import android.widget.ImageView
import coil.load

data class Image(
    private val path: String,
) {
    fun show(image: ImageView) {
        image.load(path)
    }

    fun setImage(block: (String) -> Unit) = block(path)
}