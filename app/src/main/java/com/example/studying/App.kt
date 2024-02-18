package com.example.studying

import android.app.Application
import androidx.room.Room
import com.example.studying.data.MainRepository
import com.example.studying.data.NoteDb
import com.example.studying.data.NotesDao
import com.example.studying.data.NotesDb
import com.example.studying.presentation.MainViewModel
import com.example.studying.presentation.NoteUi
import com.example.studying.utils.Mapper
import com.example.studying.utils.ToNoteDbMapper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(module {
                viewModel<MainViewModel> {
                    MainViewModel(get())
                }

                factory<MainRepository> {
                    MainRepository.Base(get(), get())
                }

                single<NotesDao> {
                    Room.databaseBuilder(get(), NotesDb::class.java, "notes_db").build().notesDao()
                }

                factory<Mapper<NoteUi.Note, NoteDb>> {
                    ToNoteDbMapper()
                }
            })
        }
    }
}