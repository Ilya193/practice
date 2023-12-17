package com.example.studying.data

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import kotlin.random.Random

interface UploadService {
    @Multipart
    @POST("upload")
    suspend fun upload(@Part image: MultipartBody.Part, @Query("id") id: Int): Int
}