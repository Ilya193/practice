package com.example.studying

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<MessagesDB> {
        Room.databaseBuilder(androidApplication(), MessagesDB::class.java, "database_messages.db").build()
    }

    single <MessagesDAO> {
        get<MessagesDB>().messagesDao()
    }

    factory<CacheDataSource> {
        CacheDataSource.Base(get())
    }

    factory<MessagesRepository> {
        MessagesRepository.Base(get())
    }

    factory<MessagesInteractor> {
        MessagesInteractor.Base(get())
    }

    factory<Communication<List<MessageUi>>> {
        Communication.Base<List<MessageUi>>()
    }

    factory<MessagesViewModel> {
        MessagesViewModel(get(), get())
    }
}