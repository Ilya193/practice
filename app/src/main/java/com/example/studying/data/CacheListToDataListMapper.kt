package com.example.studying.data


interface CacheListToDataListMapper<T, R> {
    fun map(oldList: T): R

    class Base : CacheListToDataListMapper<List<MessageCache>, List<MessageData>> {
        override fun map(oldList: List<MessageCache>): List<MessageData> {
            val newList = mutableListOf<MessageData>()
            oldList.map { data ->
                newList.add(data.map())
            }
            return newList
        }
    }
}