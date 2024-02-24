package com.example.studying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: OkHttpClient,
    private val request: Request,
) : ViewModel() {

    private val _uiState = MutableStateFlow<String>("")
    val uiState = _uiState.asStateFlow()

    fun fetch() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = client.newCall(request = request).execute()
            _uiState.value = response.body?.string() ?: ""
        } catch (e: Exception) {
            println("s149 $e")
            _uiState.value = e.message.toString()
        }
    }
}