package com.example.studying.domain

class FetchDateUseCase(
    private val repository: DateRepository
) {
    suspend operator fun invoke(day: String, month: String, year: String): ResultFDS<Int> {
        return repository.date(day, month, year)
    }
}