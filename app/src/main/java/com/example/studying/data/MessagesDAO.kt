package com.example.studying.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessagesDAO {
    @Query("SELECT * FROM Messages")
    suspend fun getAllMessages(): List<MessageCache>

    @Insert
    suspend fun insertMessage(message: MessageCache)

    @Delete
    suspend fun deleteMessage(message: MessageCache)

    @Query("SELECT * FROM Messages WHERE text LIKE :data || '%'")
    suspend fun search(data: String): List<MessageCache>
}