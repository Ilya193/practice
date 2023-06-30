package com.example.studying.domain

import com.example.studying.data.MessageData

interface DataListToDomainListMapper<T, R> {
    fun map(oldList: T): R

    class Base : DataListToDomainListMapper<List<MessageData>, List<MessageDomain>> {
        override fun map(oldList: List<MessageData>): List<MessageDomain> {
            val newList = mutableListOf<MessageDomain>()
            oldList.map { data ->
                newList.add(data.map())
            }
            return newList
        }
    }
}