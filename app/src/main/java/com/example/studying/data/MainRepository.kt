package com.example.studying.data

import com.example.studying.presentation.NoteUi
import com.example.studying.presentation.TaskUi
import com.example.studying.utils.ToNoteDbMapper
import com.example.studying.utils.ToTaskDbMapper
import io.reactivex.Completable
import io.reactivex.Flowable

interface MainRepository {
    fun getAllNotes(): Flowable<List<NoteDb>>
    fun addNote(note: NoteUi.Note): Completable
    fun update(note: NoteUi.Note): Completable
    fun deleteNote(note: NoteUi.Note): Completable
    fun fetchTasks(id: Int): Flowable<List<TaskDb>>
    fun addTask(task: TaskUi.Task): Completable
    fun deleteTask(note: TaskUi.Task): Completable


    class Base(
        private val dao: NotesDao,
        private val noteMapper: ToNoteDbMapper,
        private val taskMapper: ToTaskDbMapper
    ) : MainRepository {
        override fun getAllNotes(): Flowable<List<NoteDb>> = dao.getAllNotes()
        override fun addNote(note: NoteUi.Note): Completable = dao.addNote(noteMapper.map(note))
        override fun update(note: NoteUi.Note): Completable = dao.update(noteMapper.map(note))
        override fun deleteNote(note: NoteUi.Note): Completable = dao.deleteNote(noteMapper.map(note))
        override fun fetchTasks(id: Int): Flowable<List<TaskDb>> = dao.fetchTasks(id)
        override fun addTask(task: TaskUi.Task): Completable = dao.addTask(taskMapper.map(task))
        override fun deleteTask(note: TaskUi.Task): Completable = dao.deleteTask(taskMapper.map(note))
    }
}