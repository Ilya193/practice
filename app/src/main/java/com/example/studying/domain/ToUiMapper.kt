package com.example.studying.domain

interface ToUiMapper<T> {
    fun map(data: PostDomain): T
}