package com.example.studying.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DateService {
    @GET("{year}-{month}-{day}?cc-ru")
    suspend fun date(@Path("year") year: String, @Path("month") month: String, @Path("day") day: String): Int
}