package com.elhady.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.api.AsteroidNetwork
import com.elhady.asteroidradar.api.asAsteroidEntities
import com.elhady.asteroidradar.api.parseAsteroidsJsonResult
import com.elhady.asteroidradar.local.AppDatabase
import com.elhady.asteroidradar.local.asDomainModel
import com.elhady.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(private val database: AppDatabase) {


    val asterfoids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }
//    suspend fun getPicOfDay(apiKey: String) = AsteroidNetwork.retrofitService.getPicOfDay(apiKey)

    suspend fun getAllAsteroids(startDate: String, endDate: String, apiKey: String) =
        withContext(Dispatchers.IO){
            try {
                val asteroids = AsteroidNetwork.retrofitService.getAllAsteroids(
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


}