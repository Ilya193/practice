package com.example.studying.data

import com.example.studying.domain.DateRepository
import com.example.studying.domain.ErrorType
import com.example.studying.domain.ResultFDS
import retrofit2.HttpException
import java.net.UnknownHostException

class DateRepositoryImpl(
    private val service: DateService
): DateRepository {
    override suspend fun date(day: String, month: String, year: String): ResultFDS<Int> {
        return handleExceptions {
            val num = service.date(year, month, day)
            ResultFDS.Success(num)
        }
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