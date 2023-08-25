package com.example.studying

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

}