package com.example.studying.presentation

import com.example.studying.domain.MessageDomain

interface DomainListToUiListMapper<T, R> {
    fun map(oldList: T): R

    class Base : DomainListToUiListMapper<List<MessageDomain>, List<MessageUi>> {
        override fun map(oldList: List<MessageDomain>): List<MessageUi> {
            val newList = mutableListOf<MessageUi>()
            oldList.map { data ->
                newList.add(data.map())
            }
            return newList
        }
    }
}