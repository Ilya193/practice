package com.example.studying

import android.os.Bundle

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

        override fun map(data: Mapper<String>) {
            data.map(text)
        }

        override fun changePayload(item: MessageUi): Any {
            val bundle = Bundle()
            if (item is Base && id != item.id)
                bundle.putBoolean("newItem", true)
            return bundle
        }

        override fun map(): MessageSave = MessageSave(id, text)
    }
}