package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val users = mutableListOf<User>()

    private val _uiState = MutableLiveData<List<User>>()
    val uiState: LiveData<List<User>> get() = _uiState

    init {
        (0..20).map {
            users.add(User(name = "$it"))
        }
        _uiState.value = users.toList()
    }

    fun upload(position: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (!users[position].uploading) {
            users[position] = users[position].copy(uploading = true)
            _uiState.postValue(users.toList())
            delay(3000)
            users[position] = users[position].copy(uploading = false)
            _uiState.postValue(users.toList())
        }
    }
}