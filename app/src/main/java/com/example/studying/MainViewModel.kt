package com.example.studying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val client: HttpClient,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> get() = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = client.get("https://jsonplaceholder.typicode.com/todos")
                val todo: List<TodoCloud> = response.body()
                _uiState.value = UiState.Success(todo)
            } catch (_: Exception) {
                _uiState.value = UiState.Error()
            }
        }
    }
}

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<TodoCloud>) : UiState()
    data class Error(val msg: String = "Error") : UiState()
}