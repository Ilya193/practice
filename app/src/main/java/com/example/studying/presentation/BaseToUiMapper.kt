package com.example.studying.presentation

import com.example.studying.domain.PostDomain
import com.example.studying.domain.ToUiMapper

class BaseToUiMapper : ToUiMapper<PostUi.Success> {
    override fun map(data: PostDomain): PostUi.Success = PostUi.Success(data.userId, data.id, data.title, data.body)
}