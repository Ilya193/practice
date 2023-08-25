package com.example.studying

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: PostsService
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = service.fetchPosts()
                Log.d("attadag", list.toString())
            } catch (e: Exception) {
                Log.d("attadag", "message: ${e.message}")
                Log.d("attadag", "is ${e}")
            }
        }
    }
}