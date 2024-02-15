package com.example.studying

import io.reactivex.Completable
import io.reactivex.Flowable

interface MainRepository {
    fun getAllNotes(): Flowable<List<NoteDb>>
    fun addNote(note: NoteUi): Completable

    class Base(
        private val dao: NotesDao
    ) : MainRepository {
        override fun getAllNotes(): Flowable<List<NoteDb>> = dao.getAllNotes()
        override fun addNote(note: NoteUi): Completable = dao.addNote(note.toItemDb())
    }
}