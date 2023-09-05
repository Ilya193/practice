package com.example.studying.data

import com.example.studying.domain.PostDomain

data class PostData(
    private val userId: Int,
    private val id: Int,
    private val title: String,
    private val body: String
) {
    fun map(): PostDomain = PostDomain(userId, id, title, body)
}