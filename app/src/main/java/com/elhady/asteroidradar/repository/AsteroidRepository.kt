package com.elhady.asteroidradar.repository

import com.elhady.asteroidradar.api.AsteroidNetwork

class AsteroidRepository {

    suspend fun getPicOfDay(apiKey: String) = AsteroidNetwork.retrofitService.getPicOfDay(apiKey)


}