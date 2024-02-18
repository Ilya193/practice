package com.example.studying.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studying.presentation.NoteUi

@Entity(tableName = "notes")
data class NoteDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val isFavorite: Boolean
) {
    fun toItemUi(): NoteUi.Note = NoteUi.Note(id, text, isFavorite)
}