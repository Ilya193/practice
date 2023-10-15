package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData<StateInput>(StateInput.Success)
    val uiState: LiveData<StateInput> get() = _uiState

    fun changeState(text: String) = viewModelScope.launch(Dispatchers.IO) {
        val numbers = "0123456789"
        if (text.isEmpty()) _uiState.postValue(StateInput.Success)
        else if (text.length < 5) _uiState.postValue(StateInput.Error("length password < 5"))
        else if (!text.any { it in numbers }) _uiState.postValue(StateInput.Error("password does not contain number"))
        else _uiState.postValue(StateInput.Success)
    }
}

sealed interface StateInput {

    data class Error(val msg: String) : StateInput

    data object Success : StateInput
}