package com.example.studying

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CacheDataSource {
    suspend fun getAllMessages(): Flow<List<MessageCache>>
    suspend fun insertMessage(message: MessageCache)
    suspend fun deleteMessage(message: MessageCache)

    class Base(
        private val messagesDao: MessagesDAO,
    ) : CacheDataSource {
        override suspend fun getAllMessages(): Flow<List<MessageCache>> = flow {
            emit(messagesDao.getAllMessages())
        }

        override suspend fun insertMessage(message: MessageCache) {
            messagesDao.insertMessage(message)
        }

        override suspend fun deleteMessage(message: MessageCache) {
            messagesDao.deleteMessage(message)
        }

    }
}