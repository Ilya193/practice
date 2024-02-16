package com.example.studying

class ToNoteDbMapper : Mapper<NoteUi.Note, NoteDb> {
    override fun map(data: NoteUi.Note): NoteDb =
        NoteDb(data.id, data.text, data.isFavorite)

}