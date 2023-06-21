package com.example.studying

interface TextMapper : Mapper<Mapper<String>> {
    override fun map(data: Mapper<String>) = Unit
}

sealed class PostUi: Comparing<PostUi>, TextMapper {


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

        override fun map(data: Mapper<String>) = data.map(text)

    }

    object Loading : PostUi()
}