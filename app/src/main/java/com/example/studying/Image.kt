package com.example.studying

import android.widget.ImageView
import coil.load

sealed interface Image {

    fun show(image: ImageView) = Unit

    data class Success(
        private val path: String,
    ) : Image {
        override fun show(image: ImageView) {
            image.load(path)
        }

        fun setImage(block: (String) -> Unit) = block(path)
    }

    object NotFound : Image
}