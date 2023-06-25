package com.example.studying

data class MessageDomain(
    private val id: Int,
    private val text: String,
): ToMapper<MessageUi> {
    override fun map(): MessageUi = MessageUi.Base(id, text)
}