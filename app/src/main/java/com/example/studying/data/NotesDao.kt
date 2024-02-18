package com.example.studying.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flowable<List<NoteDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteDb): Completable
}