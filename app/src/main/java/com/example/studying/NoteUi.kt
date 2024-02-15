package com.example.studying

data class NoteUi(
    val id: Int = 0,
    val text: String,
    val isFavorite: Boolean = false
) {
    fun toItemDb(): NoteDb = NoteDb(id, text, isFavorite)
}