package com.example.studying.domain

import com.example.studying.core.ToMapper
import com.example.studying.presentation.MessageUi

data class MessageDomain(
    private val id: Int,
    private val text: String,
): ToMapper<MessageUi> {
    override fun map(): MessageUi = MessageUi.Base(id, text)
}