package com.example.studying.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.studying.presentation.NoteUi
import com.example.studying.presentation.TaskUi

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = NoteDb::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TaskDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val noteId: Int,
) {
    fun toTaskUi(): TaskUi =
        TaskUi.Task(id, text, noteId)
}