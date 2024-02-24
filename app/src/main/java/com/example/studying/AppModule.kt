package com.example.studying

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.Request

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    fun provideRequest(): Request = Request.Builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .get().build()
}