package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var startAdd = 0

    private val users = mutableListOf<User>()

    private val _uiState = MutableLiveData<List<User>>()
    val uiState: LiveData<List<User>> get() = _uiState

    init {
        (startAdd..startAdd + 50).map {
            users.add(User(name = "$it"))
        }
        startAdd += 50
        _uiState.value = users.toList()
    }

    fun add() = viewModelScope.launch(Dispatchers.IO) {
        (startAdd..startAdd + 20).map {
            users.add(User(name = "$it"))
        }
        startAdd += 20
        _uiState.postValue(users.toList())
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        val temp = mutableListOf<User>()
        (0..20).map {
            temp.add(users[it])
        }
        users.removeAll(temp)
        _uiState.postValue(users.toList())
    }
}