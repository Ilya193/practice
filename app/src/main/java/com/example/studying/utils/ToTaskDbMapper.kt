package com.example.studying.utils

import com.example.studying.data.TaskDb
import com.example.studying.presentation.TaskUi

class ToTaskDbMapper : Mapper<TaskUi.Task, TaskDb> {
    override fun map(data: TaskUi.Task): TaskDb =
        TaskDb(data.id, text = data.text, noteId = data.noteId)

}