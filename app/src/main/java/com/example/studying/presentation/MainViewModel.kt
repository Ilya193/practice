package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.domain.UploadUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val uploadUseCase: UploadUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _upload = MutableLiveData<EventWrapper<Int>>()
    val upload: LiveData<EventWrapper<Int>> get() = _upload

    fun upload(file: File, id: Int) = viewModelScope.launch(dispatcher) {
        val id = uploadUseCase(file, id)
        _upload.postValue(EventWrapper.Single(id))
    }
}