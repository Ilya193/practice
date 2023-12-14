package com.example.studying.domain

interface DateRepository {
    suspend fun date(day: String, month: String, year: String): ResultFDS<Int>
}