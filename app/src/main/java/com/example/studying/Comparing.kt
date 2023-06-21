package com.example.studying

interface Comparing<T> {
    fun same(item: T): Boolean
    fun sameContent(item: T): Boolean
}