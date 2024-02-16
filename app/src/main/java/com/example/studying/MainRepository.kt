package com.example.studying

import io.reactivex.Completable
import io.reactivex.Flowable

interface MainRepository {
    fun getAllNotes(): Flowable<List<NoteDb>>
    fun addNote(note: NoteUi.Note): Completable

    class Base(
        private val dao: NotesDao,
        private val mapper: Mapper<NoteUi.Note, NoteDb>
    ) : MainRepository {
        override fun getAllNotes(): Flowable<List<NoteDb>> = dao.getAllNotes()
        override fun addNote(note: NoteUi.Note): Completable = dao.addNote(mapper.map(note))
    }
}