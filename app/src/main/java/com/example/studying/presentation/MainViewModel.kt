package com.example.studying.presentation

import android.util.Log
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
    private var mainPosts = mutableListOf<PostUi.Success>()
    val uiState: LiveData<PostUiState> get() = _uiState

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.postValue(PostUiState.Loading)
            when (val result = postsUseCase()) {
                is Result.Success -> {
                    val posts = result.data.map {
                        mapper.map(it)
                    }
                    mainPosts = posts.map { it.copy() }.toMutableList()
                    _uiState.postValue(PostUiState.Success(posts))
                }
                is Result.Error -> {
                    _uiState.postValue(PostUiState.Error(result.e.message ?: "Error"))
                }
            }
        }
    }

    fun delete(item: PostUi.Success) {
        mainPosts.remove(item)
        val posts = mainPosts.map { it.copy() }
        _uiState.postValue(PostUiState.Success(posts))
        Log.d("attadag", "delete $posts")
    }

    fun setFavorite(item: PostUi.Success) {
        mainPosts = mainPosts.map {
            if (item.id == it.id) it.copy(isFavorite = !it.isFavorite)
            else it
        }.toMutableList()
        val posts = mainPosts.map { it.copy() }
        _uiState.postValue(PostUiState.Success(posts))
        Log.d("attadag", "setFavorite $posts")
    }
}