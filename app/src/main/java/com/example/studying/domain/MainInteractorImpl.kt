package com.example.studying.domain

class MainInteractorImpl(
    private val mainRepository: MainRepository,
) : MainInteractor {
    override suspend fun fetchPosts(): Result<List<PostDomain>> {
        return mainRepository.fetchPosts()
    }
}