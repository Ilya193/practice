package com.example.studying.domain

import com.example.studying.data.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MessagesInteractor {
    suspend fun getAllMessages(): Flow<List<MessageDomain>>
    suspend fun insertMessage(message: MessageSave)
    suspend fun deleteMessage(message: MessageSave)

    class Base(
        private val messagesRepository: MessagesRepository,
    ) : MessagesInteractor {
        override suspend fun getAllMessages(): Flow<List<MessageDomain>> = flow {
            val newList = mutableListOf<MessageDomain>()
            messagesRepository.getAllMessages().collect {
                it.map { messageData ->
                    newList.add(messageData.map())
                }
            }
            emit(newList)
        }

        override suspend fun insertMessage(message: MessageSave) {
            messagesRepository.insertMessage(message)
        }

        override suspend fun deleteMessage(message: MessageSave) {
            messagesRepository.deleteMessage(message)
        }

    }
}