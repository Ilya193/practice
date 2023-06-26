package com.example.studying.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageCache::class], version = 1)
abstract class MessagesDB : RoomDatabase() {
    abstract fun messagesDao(): MessagesDAO
}