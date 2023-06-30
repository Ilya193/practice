package com.example.studying.di

import androidx.room.Room
import com.example.studying.core.Communication
import com.example.studying.data.CacheDataSource
import com.example.studying.data.CacheListToDataListMapper
import com.example.studying.data.MessageCache
import com.example.studying.data.MessageData
import com.example.studying.data.MessagesDAO
import com.example.studying.data.MessagesDB
import com.example.studying.data.MessagesRepository
import com.example.studying.domain.DataListToDomainListMapper
import com.example.studying.domain.MessageDomain
import com.example.studying.domain.MessagesInteractor
import com.example.studying.presentation.DomainListToUiListMapper
import com.example.studying.presentation.MessageUi
import com.example.studying.presentation.MessagesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<MessagesDB> {
        Room.databaseBuilder(androidApplication(), MessagesDB::class.java, "database_messages.db")
            .build()
    }

    single<MessagesDAO> {
        get<MessagesDB>().messagesDao()
    }

    factory<CacheDataSource> {
        CacheDataSource.Base(get())
    }

    factory<CacheListToDataListMapper<List<MessageCache>, List<MessageData>>> {
        CacheListToDataListMapper.Base()
    }

    factory<DataListToDomainListMapper<List<MessageData>, List<MessageDomain>>> {
        DataListToDomainListMapper.Base()
    }

    factory<DomainListToUiListMapper<List<MessageDomain>, List<MessageUi>>> {
        DomainListToUiListMapper.Base()
    }

    factory<MessagesRepository> {
        MessagesRepository.Base(get(), get())
    }

    factory<MessagesInteractor> {
        MessagesInteractor.Base(get(), get())
    }

    factory<Communication<List<MessageUi>>> {
        Communication.Base<List<MessageUi>>()
    }

    factory<MessagesViewModel> {
        MessagesViewModel(get(), get(), get())
    }
}