package com.example.studying

import com.google.gson.annotations.SerializedName

data class PostCloud(
    @SerializedName("userId")
    private val userId: Int,
    @SerializedName("id")
    private val id: Int,
    @SerializedName("title")
    private val title: String,
    @SerializedName("body")
    private val body: String
) {

}