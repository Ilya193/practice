package com.example.studying

import android.widget.ImageView
import android.widget.TextView

sealed class NoteUi {

    open fun same(item: NoteUi): Boolean = false
    open fun sameContent(item: NoteUi): Boolean = this == item
    open fun changePayload(item: NoteUi): Boolean = false

    open fun showText(textView: TextView) {}
    open fun showIcon(imageView: ImageView) {}

    data class Note(
        val id: Int = 0,
        val text: String,
        val isFavorite: Boolean = false
    ) : NoteUi() {

        override fun same(item: NoteUi): Boolean =
            item is Note && id == item.id

        override fun changePayload(item: NoteUi): Boolean =
            item is Note && isFavorite != item.isFavorite

        override fun showText(textView: TextView) {
            textView.text = text
        }

        override fun showIcon(imageView: ImageView) {
            imageView.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }

    }

    sealed class Header : NoteUi() {
        override fun same(item: NoteUi): Boolean = this.toString() == item.toString()

        data object Favorite : Header() {
            override fun showText(textView: TextView) {
                textView.text = textView.context.getString(R.string.header)
            }
        }
        data object Ordinary : Header() {
            override fun showText(textView: TextView) {
                textView.text = textView.context.getString(R.string.ordinary)
            }
        }
    }
}