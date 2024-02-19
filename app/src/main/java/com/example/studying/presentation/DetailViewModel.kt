package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studying.data.MainRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<TaskUiState>()
    val uiState: LiveData<TaskUiState> get() = _uiState

    private var disposable: Disposable? = null

    fun fetchTasks(id: Int) {
        disposable = repository.fetchTasks(id)
            .subscribeOn(Schedulers.io())
            .flatMap {
                Flowable.fromIterable(it)
                    .map { it.toTaskUi() }
                    .toList()
                    .toFlowable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tasks ->
                if (tasks.isEmpty()) _uiState.value = TaskUiState.Empty
                else _uiState.value = TaskUiState.Success(tasks)
            }
    }

    fun addTask(task: String, noteId: Int) {
        repository.addTask(TaskUi.Task(text = task, noteId = noteId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun delete(task: TaskUi.Task) {
        repository.deleteTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}