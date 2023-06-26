package com.example.studying.presentation

import android.os.Bundle
import com.example.studying.domain.MessageSave
import com.example.studying.core.Comparing
import com.example.studying.core.Mapper
import com.example.studying.core.ToMapper

interface StringMapper : Mapper<Mapper<String>>

sealed class MessageUi : Comparing<MessageUi>, StringMapper, ToMapper<MessageSave> {

    override fun same(item: MessageUi): Boolean = false

    override fun sameContent(item: MessageUi): Boolean = false

    class Base(
        private val id: Int,
        private val text: String,
    ) : MessageUi() {
        override fun same(item: MessageUi): Boolean = item is Base && id == item.id
        override fun sameContent(item: MessageUi): Boolean = this == item

        override fun changePayload(item: MessageUi): Any {
            val bundle = Bundle()
            if (item is Base && id != item.id)
                bundle.putBoolean("newItem", true)
            return bundle
        }

        override fun map(data: Mapper<String>) {
            data.map(text)
        }

        override fun map(): MessageSave = MessageSave(id, text)
    }
}