package com.example.studying

sealed class PostUi: Comparing<PostUi> {

    override fun same(item: PostUi): Boolean = false
    override fun sameContent(item: PostUi): Boolean = false

    data class Base(
        val id: Int = 0,
        val text: String = ""
    ): PostUi() {
        override fun same(item: PostUi): Boolean =
            item is Base && id == item.id

        override fun sameContent(item: PostUi): Boolean =
            this == item

    }

    object Loading : PostUi()
}