package com.example.studying

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigation: Navigation<Screen>
) : ViewModel() {

    fun init(first: Boolean) {
        if (first) navigation.update(InitScreen())
    }

    fun liveData() = navigation.read()
}