package com.example.studying

import android.app.Application
import com.example.studying.data.UploadRepositoryImpl
import com.example.studying.data.UploadService
import com.example.studying.domain.UploadRepository
import com.example.studying.domain.UploadUseCase
import com.example.studying.domain.ResourceProvider
import com.example.studying.presentation.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
        MainViewModel(get())
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    factory<ResourceProvider> {
        ResourceProvider.Base(get())
    }

    factory<UploadUseCase> {
        UploadUseCase(get())
    }

    single<UploadService> {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.6:8080")
            .addConverterFactory(get())
            .build()
            .create(UploadService::class.java)
    }

    factory<UploadRepository> {
        UploadRepositoryImpl(get())
    }
}