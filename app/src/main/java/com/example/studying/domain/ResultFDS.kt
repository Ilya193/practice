package com.example.studying.domain

sealed class ResultFDS<out T> {

    data class Success<T>(
        val data: T
    ): ResultFDS<T>()

    data class Error(
        val e: ErrorType
    ): ResultFDS<Nothing>()

}