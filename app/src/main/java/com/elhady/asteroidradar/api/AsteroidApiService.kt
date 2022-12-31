package com.elhady.asteroidradar.api

import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.model.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface AsteroidApiService {

    @GET("planetary/apod")
    suspend fun getPicOfDay(@Query("api_key") apiKey: String): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getAllAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): String


}

/**
 * declare singleton objects.
 * Singleton pattern ensures that one, and only one, instance of an object is created
 */
object AsteroidApi {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        /** the Retrofit scalar converter.
         *  This converter enables Retrofit to return the JSON result as a String
         */
        .addConverterFactory(ScalarsConverterFactory.create())
        /**  The Retrofit Moshi converter,
         *   is an Android JSON parser that converts a JSON string into Kotlin objects.
         */
        .addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(Constants.BASE_URL)
        .build()

    /**
     * Make this lazy initialization, to make sure it is initialized at its first usage.
     */
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}



