package com.example.studying.data

import com.example.studying.domain.MessageSave
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


interface MessagesRepository {
    suspend fun getAllMessages(): Flow<List<MessageData>>
    suspend fun insertMessage(message: MessageSave)
    suspend fun deleteMessage(message: MessageSave)
    suspend fun search(text: String): Flow<List<MessageData>>


    class Base(
        private val cacheDataSource: CacheDataSource,
        private val listToList: CacheListToDataListMapper<List<MessageCache>, List<MessageData>>,
    ) : MessagesRepository {
        override suspend fun getAllMessages(): Flow<List<MessageData>> = flow {
            cacheDataSource.getAllMessages().collect {
                emit(listToList.map(it))
            }
        }

        override suspend fun insertMessage(message: MessageSave) {
            cacheDataSource.insertMessage(MessageCache(text = message.text))
        }

        override suspend fun deleteMessage(message: MessageSave) {
            cacheDataSource.deleteMessage(MessageCache(id = message.id, text = message.text))
        }

        override suspend fun search(text: String): Flow<List<MessageData>> = flow {
            cacheDataSource.search(text).collect {
                emit(listToList.map(it))
            }
        }
    }
}