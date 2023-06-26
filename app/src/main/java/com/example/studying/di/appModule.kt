package com.example.studying.di

import androidx.room.Room
import com.example.studying.data.CacheDataSource
import com.example.studying.core.Communication
import com.example.studying.presentation.MessageUi
import com.example.studying.data.MessagesDAO
import com.example.studying.data.MessagesDB
import com.example.studying.domain.MessagesInteractor
import com.example.studying.data.MessagesRepository
import com.example.studying.presentation.MessagesViewModel
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