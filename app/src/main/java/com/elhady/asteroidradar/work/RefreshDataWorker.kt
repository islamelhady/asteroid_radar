package com.elhady.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.local.AppDatabase
import com.elhady.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        try {
            repository.getAllAsteroids(
                Constants.getToday(),
                Constants.getSevenDaysLater(),
                Constants.API_KEY
            )
            repository.deletePreviousDay(Constants.getPreviousDay())
            Timber.d("Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.elhady.asteroidradar.work.RefreshDataWorker"
    }
}