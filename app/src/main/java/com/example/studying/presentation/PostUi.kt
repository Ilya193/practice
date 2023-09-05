package com.example.studying.presentation

sealed class PostUi {

    data class Success(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
    ) : PostUi()

    data class Error(
        val message: String
    ) : PostUi()
}