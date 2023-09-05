package com.example.studying.domain

interface MainRepository {
    suspend fun fetchPosts(): Result<List<PostDomain>>
}