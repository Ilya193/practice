package com.example.studying.data

import retrofit2.http.GET

interface PostsService {
    @GET("posts")
    suspend fun fetchPosts(): List<PostCloud>
}