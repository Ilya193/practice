package com.example.studying

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val navigation: Navigation<Screen>
) : ViewModel() {

    fun init(first: Boolean) {
        if (first) navigation.update(InitScreen())
    }

    fun update(screen: Screen) = navigation.update(screen)

    fun liveData() = navigation.read()
}