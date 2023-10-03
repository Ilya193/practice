package com.example.studying.presentation

sealed class PostUi {

    data class Success(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
        val isFavorite: Boolean = false
    ) : PostUi(), Comparing<Success> {
        override fun same(item: Success): Boolean = id == item.id
        override fun sameContent(item: Success): Boolean = this == item
        override fun changePayload(item: Success): Boolean = isFavorite != item.isFavorite
    }
}

sealed class PostUiState {
    data class Success(val data: List<PostUi.Success>) : PostUiState()

    data class Error(
        val message: String
    ) : PostUiState()

    object Loading : PostUiState()
}