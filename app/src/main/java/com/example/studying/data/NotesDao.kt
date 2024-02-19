package com.example.studying.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flowable<List<NoteDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteDb): Completable

    @Update
    fun update(note: NoteDb): Completable

    @Delete
    fun deleteNote(note: NoteDb): Completable

    @Query("SELECT * FROM tasks where noteId = :id")
    fun fetchTasks(id: Int): Flowable<List<TaskDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: TaskDb): Completable

    @Delete
    fun deleteTask(note: TaskDb): Completable
}