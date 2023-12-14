package com.example.studying

import android.app.Application
import com.example.studying.data.DateRepositoryImpl
import com.example.studying.data.DateService
import com.example.studying.domain.DateRepository
import com.example.studying.domain.FetchDateUseCase
import com.example.studying.domain.ResourceProvider
import com.example.studying.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(module)
        }
    }
}

val module = module {
    viewModel<MainViewModel> {
        MainViewModel(get(), get())
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    factory<ResourceProvider> {
        ResourceProvider.Base(get())
    }

    factory<FetchDateUseCase> {
        FetchDateUseCase(get())
    }

    single<DateService> {
        Retrofit.Builder()
            .baseUrl("https://isdayoff.ru/")
            .addConverterFactory(get())
            .build()
            .create(DateService::class.java)
    }

    factory<DateRepository> {
        DateRepositoryImpl(get())
    }
}