package com.example.studying

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel<MainViewModel> {
        MainViewModel()
    }
}