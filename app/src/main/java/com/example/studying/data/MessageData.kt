package com.example.studying.data

import com.example.studying.domain.MessageDomain
import com.example.studying.core.ToMapper

data class MessageData(
    private val id: Int,
    private val text: String,
): ToMapper<MessageDomain> {
    override fun map(): MessageDomain = MessageDomain(id, text)
}