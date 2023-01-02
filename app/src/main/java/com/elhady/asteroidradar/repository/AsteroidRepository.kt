package com.elhady.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.elhady.asteroidradar.api.AsteroidApi
import com.elhady.asteroidradar.api.asAsteroidEntities
import com.elhady.asteroidradar.api.parseAsteroidsJsonResult
import com.elhady.asteroidradar.local.AppDatabase
import com.elhady.asteroidradar.local.asDomainModel
import com.elhady.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(private val database: AppDatabase) {


    val listAsteroidLiveData: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun getAllAsteroids(startDate: String, endDate: String, apiKey: String) =
        withContext(Dispatchers.IO){
            try {
                val asteroids = AsteroidApi.retrofitService.getAllAsteroids(
                    startDate,
                    endDate,
                    apiKey
                )
                val asteroidsList = parseAsteroidsJsonResult(JSONObject(asteroids))
                database.asteroidDao.insertAsteroids(asteroidsList.asAsteroidEntities())
            } catch (e: Exception) {
                Timber.i("error ${e.message}")
            }
        }

    fun getTodayAsteroids(todayDate: String) = database.asteroidDao.getTodayAsteroids(todayDate)

    fun getWeekAsteroids(startDate: String, endDate: String) = database.asteroidDao.getWeekAsteroids(startDate, endDate)

    fun deletePreviousDay(previousDay: String) = database.asteroidDao.deletePreviousDay(previousDay)


}