package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.domain.DateRepository
import com.example.studying.domain.FetchDateUseCase
import com.example.studying.domain.ResourceProvider
import com.example.studying.domain.ResultFDS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchDateUseCase: FetchDateUseCase,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _date = MutableLiveData<DateUiState>()
    val date: LiveData<DateUiState> get() = _date

    fun date(day: String, month: String, year: String) = viewModelScope.launch(dispatcher) {
        _date.postValue(DateUiState.Loading)
        when (val result = fetchDateUseCase(day, month, year)) {
            is ResultFDS.Success -> _date.postValue(result.data.result())
            is ResultFDS.Error -> _date.postValue(DateUiState.Error(resourceProvider.getString(result.e)))
        }
    }

    private fun Int.result(): DateUiState {
        return when (this) {
            0 -> DateUiState.Success("рабочий день")
            1 -> DateUiState.Success("не рабочий день")
            100 -> DateUiState.Error("Произошла ошибка")
            else -> DateUiState.Error("Произошла ошибка")
        }
    }
}



sealed interface DateUiState {
    data class Success(val result: String): DateUiState
    data class Error(val msg: String): DateUiState
    data object Loading : DateUiState
}