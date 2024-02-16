package com.example.studying

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val isFavorite: Boolean
) {
    fun toItemUi(): NoteUi.Note = NoteUi.Note(id, text, isFavorite)
}