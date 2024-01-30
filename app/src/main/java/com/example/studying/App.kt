package com.example.studying

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

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

    viewModel<FirstViewModel> {
        FirstViewModel(get())
    }

    val navigation = Navigation.Base()

    single<Navigation<Screen>> {
        navigation
    }

    factory<FirstRouter> {
        navigation
    }
}