package com.example.studying.domain

interface MainInteractor {
    suspend fun fetchPosts(): Result<List<PostDomain>>
}