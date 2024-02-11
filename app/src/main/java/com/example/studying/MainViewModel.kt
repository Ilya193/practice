package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val users = mutableListOf<User>()

    private val _uiState = MutableLiveData<List<User>>()
    val uiState: LiveData<List<User>> get() = _uiState

    init {
        (0..50).map {
            users.add(User())
        }
        _uiState.value = users.toList()
    }

    fun changeTextHidden(position: Int, newText: String) {
        users[position] = users[position].copy(name = newText)
        _uiState.value = users.toList()
    }

    fun changeText(position: Int, newText: String) {
        users[position] = users[position].copy(name = newText)
    }

    fun coup() {
        _uiState.value = users.toList()
    }
}