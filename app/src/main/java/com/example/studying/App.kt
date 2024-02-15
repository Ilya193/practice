package com.example.studying

import android.app.Application
import androidx.room.Room
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
                    MainRepository.Base(get())
                }

                single<NotesDao> {
                    Room.databaseBuilder(get(), NotesDb::class.java, "notes_db").build().notesDao()
                }
            })
        }
    }
}