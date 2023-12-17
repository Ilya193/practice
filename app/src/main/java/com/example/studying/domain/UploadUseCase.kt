package com.example.studying.domain

import java.io.File

class UploadUseCase(
    private val repository: UploadRepository
) {
    suspend operator fun invoke(file: File, id: Int): Int {
        return repository.upload(file, id)
    }
}