package com.example.studying.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studying.domain.MessageSave
import com.example.studying.domain.MessagesInteractor
import com.example.studying.core.Communication
import com.example.studying.domain.MessageDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MessagesViewModel(
    private val messagesInteractor: MessagesInteractor,
    private val communication: Communication<List<MessageUi>>,
    private val listToList: DomainListToUiListMapper<List<MessageDomain>, List<MessageUi>>
) : ViewModel() {

    init {
        getAllMessages()
    }

    private fun getAllMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            messagesInteractor.getAllMessages().collect {
                val uiList = listToList.map(it)
                withContext(Dispatchers.Main) {
                    communication.map(uiList)
                }
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


    fun search(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messagesInteractor.search(text).collect {
                val uiList = listToList.map(it)
                withContext(Dispatchers.Main) {
                    communication.map(uiList)
                }
            }
        }
    }
}