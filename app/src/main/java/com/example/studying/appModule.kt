package com.example.studying

import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<FirebaseDatabase> {
        FirebaseDatabase.getInstance()
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }

}