package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.domain.FetchPostsUseCase
import com.example.studying.domain.Result
import com.example.studying.domain.ToUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val postsUseCase: FetchPostsUseCase,
    private val mapper: ToUiMapper<PostUi.Success>
) : ViewModel() {

    private val _uiState = MutableLiveData<PostUiState>()
    val uiState: LiveData<PostUiState> get() = _uiState

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            //_uiState.postValue(PostUiState.Loading)
            when (val result = postsUseCase()) {
                is Result.Success -> {
                    val posts = result.data.map {
                        mapper.map(it)
                    }
                    _uiState.postValue(PostUiState.Success(posts))
                }
                is Result.Error -> {
                    _uiState.postValue(PostUiState.Error(result.e.message ?: "Error"))
                }
            }
        }
    }
}