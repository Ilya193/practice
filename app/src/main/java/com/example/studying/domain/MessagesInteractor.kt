package com.example.studying.domain

import com.example.studying.data.MessageData
import com.example.studying.data.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MessagesInteractor {
    suspend fun getAllMessages(): Flow<List<MessageDomain>>
    suspend fun insertMessage(message: MessageSave)
    suspend fun deleteMessage(message: MessageSave)

    suspend fun search(text: String): Flow<List<MessageDomain>>

    class Base(
        private val messagesRepository: MessagesRepository,
        private val listToList: DataListToDomainListMapper<List<MessageData>, List<MessageDomain>>,
    ) : MessagesInteractor {
        override suspend fun getAllMessages(): Flow<List<MessageDomain>> = flow {
            messagesRepository.getAllMessages().collect {
                emit(listToList.map(it))
            }
        }

        override suspend fun insertMessage(message: MessageSave) {
            messagesRepository.insertMessage(message)
        }

        override suspend fun deleteMessage(message: MessageSave) {
            messagesRepository.deleteMessage(message)
        }

        override suspend fun search(text: String): Flow<List<MessageDomain>> = flow {
            messagesRepository.search(text).collect {
                emit(listToList.map(it))
            }
        }

    }
}