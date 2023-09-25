package com.example.studying.domain

class FetchPostsUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(): Result<List<PostDomain>> {
        return repository.fetchPosts()
    }
}