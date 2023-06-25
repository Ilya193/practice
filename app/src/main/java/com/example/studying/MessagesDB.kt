package com.example.studying

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageCache::class], version = 1)
abstract class MessagesDB : RoomDatabase() {
    abstract fun messagesDao(): MessagesDAO
}