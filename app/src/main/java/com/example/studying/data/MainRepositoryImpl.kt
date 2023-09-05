package com.example.studying.data

import com.example.studying.domain.MainRepository
import com.example.studying.domain.PostDomain
import com.example.studying.domain.PostsDomain
import com.example.studying.domain.Result
import java.net.UnknownHostException

class MainRepositoryImpl(
    private val postsService: PostsService,
) : MainRepository {
    override suspend fun fetchPosts(): Result<List<PostDomain>> {
        return try {
            Result.Success(postsService.fetchPosts().map { it.map() }.map {it.map()})
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}