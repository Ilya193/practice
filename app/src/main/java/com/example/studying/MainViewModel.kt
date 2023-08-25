package com.example.studying

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: PostsService
) : ViewModel() {

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = service.fetchPosts()
            } catch (e: Exception) {
                Log.d("attadag", "message: ${e.message}")
            }
        }
    }
}