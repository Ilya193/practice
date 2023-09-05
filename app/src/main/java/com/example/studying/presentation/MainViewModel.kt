package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.EventWrapper
import com.example.studying.domain.MainInteractor
import com.example.studying.domain.Result
import com.example.studying.domain.ToUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: MainInteractor,
    private val mapper: ToUiMapper<PostUi.Success>
) : ViewModel() {

    private val _success = MutableLiveData<List<PostUi.Success>>()
    val success: LiveData<List<PostUi.Success>> get() = _success

    private val _error = MutableLiveData<EventWrapper.Change<PostUi.Error>>()
    val error: LiveData<EventWrapper.Change<PostUi.Error>> get() = _error
    private val event = EventWrapper.Change(PostUi.Error(""))

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = interactor.fetchPosts()) {
                is Result.Success -> {
                    val posts = result.data.map {
                        mapper.map(it)
                    }
                    _success.postValue(posts)
                }
                is Result.Error -> {
                    event.setState(false)
                    event.setValue(PostUi.Error(result.e.message ?: "Error"))
                    _error.postValue(event)
                }
            }
        }
    }
}