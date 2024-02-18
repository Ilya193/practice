package com.example.studying.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studying.data.MainRepository
import io.reactivex.Flowable
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
            .flatMap { list ->
                var firstFavorite = true
                var firstOrdinary = true
                Flowable.fromIterable(list)
                    .map { it.toItemUi() }
                    .sorted { o1, o2 ->
                        o2.isFavorite.compareTo(o1.isFavorite)
                    }
                    .concatMap { item ->
                        if (item.isFavorite && firstFavorite) {
                            firstFavorite = false
                            Flowable.just(NoteUi.Header.Favorite, item)
                        }
                        else if (!item.isFavorite && firstOrdinary) {
                            firstOrdinary = false
                            Flowable.just(NoteUi.Header.Ordinary, item)
                        }
                        else Flowable.just(item)
                    }
                    .toList()
                    .toFlowable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                _uiState.value = notes
            }
    }

    fun addNote(text: String) {
        addNote(NoteUi.Note(text = text))
    }

    fun favorite(item: NoteUi.Note) {
        addNote(item.copy(isFavorite = !item.isFavorite))
    }

    private fun addNote(note: NoteUi.Note) {
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