package com.elhady.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val PREVIOUS_DAY = -1
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = BuildConfig.API_KEY


    fun getToday(): String {
        val currentTime = Calendar.getInstance().time
        val formatter  = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return formatter.format(currentTime)
    }

    fun getSevenDaysLater(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
        val currentTime = calendar.time
        val formatter = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return formatter.format(currentTime)
    }

    fun getPreviousDay(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, PREVIOUS_DAY)
        val currentTime = calendar.time
        val formatter = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return formatter.format(currentTime)
    }
}

