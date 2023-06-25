package com.example.studying

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MessagesViewModel(
    private val messagesInteractor: MessagesInteractor,
    private val communication: Communication<List<MessageUi>>,
) : ViewModel() {

    init {
        getAllMessages()
    }

    private fun getAllMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            val newList = mutableListOf<MessageUi>()
            messagesInteractor.getAllMessages().collect {
                it.map { messageDomain ->
                    newList.add(messageDomain.map())
                }
            }

            withContext(Dispatchers.Main) {
                communication.map(newList)
            }
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<List<MessageUi>>) {
        communication.observe(lifecycleOwner, observer)
    }

    fun saveMessage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messagesInteractor.insertMessage(MessageSave(text = text))
            getAllMessages()
        }
    }

    fun deleteMessage(message: MessageUi) {
        viewModelScope.launch(Dispatchers.IO) {
            messagesInteractor.deleteMessage(message.map())
            getAllMessages()
        }
    }
}