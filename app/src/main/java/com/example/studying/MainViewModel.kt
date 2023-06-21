package com.example.studying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _list = MutableStateFlow<List<PostUi>>(listOf())
    val list: StateFlow<List<PostUi>> get() = _list

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchData().collect {
                _list.value = it
            }
        }
    }

    private fun fetchData(): Flow<List<PostUi>> = flow {
        emit(listOf(PostUi.Loading))
        val list = mutableListOf<PostUi>()
        for (i in 0..10) {
            list.add(
                PostUi.Base(
                    i,
                    "Persecuti error taciti elit prodesset graeco dolores. Imperdiet mutat dolor brute ridiculus."
                )
            )
        }
        emit(list)
    }
}