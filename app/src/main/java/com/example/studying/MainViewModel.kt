package com.example.studying

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: OkHttpClient,
    private val request: Request,
) : ViewModel() {

    val uiState = flow {
        val response = client.newCall(request = request).execute()
        emit(response.body?.string() ?: "")
    }.flowOn(Dispatchers.IO).catch {
        emit(it.message.toString())
    }
}