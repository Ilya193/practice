package com.example.studying

import androidx.lifecycle.ViewModel

class SecondViewModel(
    private val secondRouter: SecondRouter
) : ViewModel() {

    fun comeback() = secondRouter.comeback()

    fun coup() = secondRouter.coup()
}