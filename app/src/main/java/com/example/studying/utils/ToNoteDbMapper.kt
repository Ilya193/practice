package com.example.studying.utils

import com.example.studying.presentation.NoteUi
import com.example.studying.data.NoteDb

class ToNoteDbMapper : Mapper<NoteUi.Note, NoteDb> {
    override fun map(data: NoteUi.Note): NoteDb =
        NoteDb(data.id, data.text, data.isFavorite)

}