package com.example.studying

interface Mapper<T, R> {
    fun map(data: T): R
}