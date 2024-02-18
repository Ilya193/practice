package com.example.studying.utils

interface Mapper<T, R> {
    fun map(data: T): R
}