package com.example.studying.data

import com.example.studying.domain.ErrorType
import com.example.studying.domain.ResultFDS
import com.example.studying.domain.UploadRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.net.UnknownHostException

class UploadRepositoryImpl(
    private val service: UploadService,
) : UploadRepository {
    override suspend fun upload(file: File) {
        service.upload(
            MultipartBody.Part.createFormData(
                "file", file.name, file.asRequestBody("multipart/form-data".toMediaType())
            )
        )
    }

    private suspend fun <T> handleExceptions(block: suspend () -> ResultFDS<T>): ResultFDS<T> {
        return try {
            block()
        } catch (e: UnknownHostException) {
            ResultFDS.Error(ErrorType.NO_CONNECTION)
        } catch (e: HttpException) {
            ResultFDS.Error(ErrorType.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            ResultFDS.Error(ErrorType.GENERIC_ERROR)
        }
    }
}