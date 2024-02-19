package com.example.studying.presentation

import android.widget.TextView
import com.example.studying.R

sealed class NoteUi: DelegateItem {

    open fun same(item: NoteUi): Boolean = false
    open fun sameContent(item: NoteUi): Boolean = this == item
    open fun changePayload(item: NoteUi): Boolean = false

    data class Note(
        val id: Int = 0,
        val text: String,
        val isFavorite: Boolean = false
    ) : NoteUi() {

        override fun same(item: NoteUi): Boolean =
            item is Note && id == item.id

        override fun changePayload(item: NoteUi): Boolean =
            item is Note && isFavorite != item.isFavorite

        override fun changePayload(item: DelegateItem): Any =
            isFavorite != (item as Note).isFavorite

        override fun id(item: DelegateItem): Boolean =
            id == (item as Note).id

        override fun compareTo(item: DelegateItem): Boolean {
            return (item as Note) == this
        }

    }

    sealed class Header : NoteUi() {

        open fun showText(textView: TextView, text: Int = 0) {
            textView.text = textView.context.getString(text)
        }

        override fun changePayload(item: DelegateItem): Any = false

        override fun id(item: DelegateItem): Boolean = this.toString() == (item as Header).toString()


        override fun compareTo(item: DelegateItem): Boolean {
            return (item as Header) == this
        }

        override fun same(item: NoteUi): Boolean = this.toString() == item.toString()

        data object Favorite : Header() {
            override fun showText(textView: TextView, text: Int) {
                super.showText(textView, R.string.header)
            }
        }
        data object Ordinary : Header() {
            override fun showText(textView: TextView, text: Int) {
                super.showText(textView, R.string.ordinary)
            }
        }
    }
}

sealed interface NoteUiState {
    data class Success(val data: List<NoteUi>) : NoteUiState
    data object Empty : NoteUiState
}