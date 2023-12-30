package com.example.studying.data

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class PostCloud(
    @Json(name = "userId")
    private val userId: Int,
    @Json(name = "id")
    private val id: Int,
    @Json(name = "title")
    private val title: String,
    @Json(name = "body")
    private val body: String
) {
    fun map(): PostData = PostData(userId, id, title, body)
}