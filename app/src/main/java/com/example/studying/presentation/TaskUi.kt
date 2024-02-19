package com.example.studying.presentation

sealed class TaskUi : DelegateItem {

    override fun changePayload(item: DelegateItem): Any = false

    data class Task(
        val id: Int = 0,
        val text: String,
        val noteId: Int
    ) : TaskUi() {

        override fun id(item: DelegateItem): Boolean =
            id == (item as Task).id

        override fun compareTo(item: DelegateItem): Boolean {
            return (item as Task) == this
        }

    }
}
