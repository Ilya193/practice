package com.example.studying

import com.example.studying.data.MainRepositoryImpl
import com.example.studying.data.PostsService
import com.example.studying.domain.MainInteractor
import com.example.studying.domain.MainInteractorImpl
import com.example.studying.domain.MainRepository
import com.example.studying.domain.ToUiMapper
import com.example.studying.presentation.BaseToUiMapper
import com.example.studying.presentation.MainViewModel
import com.example.studying.presentation.PostUi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    /*single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(get())
            .build()
    }*/

    single<PostsService> {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(get())
            .build()
            .create(PostsService::class.java)
    }

    factory<MainRepository> { MainRepositoryImpl(get()) }

    factory<MainInteractor> {
        MainInteractorImpl(get())
    }

    factory<ToUiMapper<PostUi.Success>> { BaseToUiMapper() }

    viewModel<MainViewModel> {
        MainViewModel(get(), get())
    }

}