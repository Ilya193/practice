package com.example.studying

data class MessageData(
    private val id: Int,
    private val text: String,
): ToMapper<MessageDomain> {
    override fun map(): MessageDomain = MessageDomain(id, text)
}