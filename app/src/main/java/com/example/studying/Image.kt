package com.example.studying

sealed interface Image {

    data class Success(
        val path: String
    ) : Image

    object NotFound : Image
}