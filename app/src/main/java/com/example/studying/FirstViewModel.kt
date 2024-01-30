package com.example.studying

import androidx.lifecycle.ViewModel

class FirstViewModel(
    private val firstRouter: FirstRouter
) : ViewModel() {

    fun coup() = firstRouter.coup()
}