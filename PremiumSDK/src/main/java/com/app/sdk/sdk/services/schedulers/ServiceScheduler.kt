package com.app.sdk.sdk.services.schedulers

import android.content.Context
import androidx.work.*
import com.app.sdk.sdk.services.ProxyService
import java.util.*
import java.util.concurrent.TimeUnit

private const val WORKER_NAME = "Launcher"

class ServiceScheduler(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        ProxyService.startService(context)
        return Result.success()
    }

    companion object {

        fun launchSingle(context: Context) {

            val oneTimeWorkRequest = OneTimeWorkRequest
                .Builder(ServiceScheduler::class.java)
                .setConstraints(getConstrains())
                .setInitialDelay(10, TimeUnit.MILLISECONDS)
                .setInputData(Data.Builder().putLong("temp", Date().time).build()).build()

            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(WORKER_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
        }

        private fun getConstrains() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .setRequiresStorageNotLow(false)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false).build()
    }
}