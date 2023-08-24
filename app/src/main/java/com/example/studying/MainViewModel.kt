package com.example.studying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {
    private val _list = MutableStateFlow<List<PostUi>>(listOf())
    val list: StateFlow<List<PostUi>> get() = _list

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchData()
        }
    }

    private suspend fun fetchData(): List<PostUi> {
        return suspendCoroutine { continuation ->
            val cds = TestCloudDataSource.Base(object : Result<List<PostUi>> {
                override fun success(data: List<PostUi>) {
                    continuation.resume(data)
                }

                override fun error(e: Exception) {
                    continuation.resumeWithException(e)
                }
            })
            cds.fetchData()
        }
    }
}

interface TestCloudDataSource {
    fun fetchData()

    class Base(private val result: Result<List<PostUi>>) : TestCloudDataSource {
        override fun fetchData() {
            val list = mutableListOf<PostUi>()
            for (i in 0..100) {
                list.add(
                    PostUi.Base(
                        i,
                        "Persecuti error taciti elit prodesset graeco dolores. Imperdiet mutat dolor brute ridiculus."
                    )
                )
            }
            result.success(list)
        }
    }
}

interface Result<T> {
    fun success(data: T)
    fun error(e: Exception)
}