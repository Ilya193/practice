package com.example.studying.domain

import java.io.File

interface UploadRepository {
    suspend fun upload(file: File)
}