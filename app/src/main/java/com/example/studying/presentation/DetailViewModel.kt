package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studying.data.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<List<TaskUi>>()
    val uiState: LiveData<List<TaskUi>> get() = _uiState

    private var disposable: Disposable? = null

    fun fetchTasks(id: Int) {
        disposable = repository.fetchTasks(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _uiState.value = it.map { it.toTaskUi() }
            }
    }

    fun addTask(task: String, noteId: Int) {
        repository.addTask(TaskUi.Task(text = task, noteId = noteId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}