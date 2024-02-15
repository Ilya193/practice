package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repository: MainRepository
): ViewModel() {

    private val _uiState = MutableLiveData<List<NoteUi>>()
    val uiState: LiveData<List<NoteUi>> get() = _uiState

    private var disposable: Disposable? = null

    fun fetchNotes() {
        disposable = repository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                _uiState.value = notes.map { it.toItemUi() }
            }
    }

    fun addNote(text: String) {
        addNote(NoteUi(text = text))
    }

    fun favorite(item: NoteUi) {
        addNote(item.copy(isFavorite = !item.isFavorite))
    }

    private fun addNote(note: NoteUi) {
        repository.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}