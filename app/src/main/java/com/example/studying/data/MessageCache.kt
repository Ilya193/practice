package com.example.studying.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studying.core.ToMapper


@Entity(tableName = "Messages")
data class MessageCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("text")
    val text: String
) : ToMapper<MessageData> {
    override fun map(): MessageData = MessageData(id, text)
}