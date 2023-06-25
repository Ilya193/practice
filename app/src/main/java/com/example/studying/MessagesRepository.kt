package com.example.studying

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MessagesRepository {
    suspend fun getAllMessages(): Flow<List<MessageData>>
    suspend fun insertMessage(message: MessageSave)
    suspend fun deleteMessage(message: MessageSave)

    class Base(
        private val cacheDataSource: CacheDataSource,
    ) : MessagesRepository {
        override suspend fun getAllMessages(): Flow<List<MessageData>> = flow {
            val newList = mutableListOf<MessageData>()
            cacheDataSource.getAllMessages().collect {
                it.map { messageCache ->
                    newList.add(messageCache.map())
                }
            }
            emit(newList)
        }

        override suspend fun insertMessage(message: MessageSave) {
            cacheDataSource.insertMessage(MessageCache(text = message.text))
        }

        override suspend fun deleteMessage(message: MessageSave) {
            cacheDataSource.deleteMessage(MessageCache(id = message.id, text = message.text))
        }

    }
}