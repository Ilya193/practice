package com.example.studying

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val data = mutableListOf<ItemUi>()
    private val _uiState = MutableStateFlow(data.toList())
    val uiState = _uiState.asStateFlow()

    init {
        println("s149 INIT")
        data.addAll((0..3).map {
            ItemUi(it.toLong(), Random.nextInt())
        })
        _uiState.value = data.toList()
    }

    fun add() {
        data.add(ItemUi(System.currentTimeMillis(), Random.nextInt()))
        _uiState.value = data.toList()
    }
}

data class ItemUi(val id: Long, val num: Int)