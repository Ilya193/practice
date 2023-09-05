package com.example.studying.data

import com.example.studying.domain.PostDomain

interface ToDomainMapper<T> {
    fun map(userId: Int, id: Int, title: String, body: String): T

    class Base : ToDomainMapper<PostDomain> {
        override fun map(userId: Int, id: Int, title: String, body: String): PostDomain =
            PostDomain(userId, id, title, body)
    }
}