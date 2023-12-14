package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.domain.UploadUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val uploadUseCase: UploadUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _upload = MutableLiveData<String>()
    val upload: LiveData<String> get() = _upload

    fun upload(file: File) = viewModelScope.launch(dispatcher) {
        uploadUseCase(file)
    }
}
