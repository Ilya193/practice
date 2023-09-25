package com.example.studying.presentation

sealed class PostUi : Comparing<PostUi> {

    override fun same(item: PostUi): Boolean = false

    override fun sameContent(item: PostUi): Boolean = false

    data class Success(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
    ) : PostUi() {
        override fun same(item: PostUi): Boolean = item is Success && id == item.id
        override fun sameContent(item: PostUi): Boolean = this == item
    }
}

sealed class PostUiState {
    data class Success(val data: List<PostUi>) : PostUiState()

    data class Error(
        val message: String
    ) : PostUiState()

    object Loading : PostUiState()
}