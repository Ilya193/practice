package com.example.studying

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class TestWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
            .putInt("number", Random.nextInt()).apply()

        return Result.success()
    }
}