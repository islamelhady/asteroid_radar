package com.elhady.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = REPLACE)
    fun insertAsteroids( asteroid: List<DatabaseAsteroid>)

    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate == :todayDate ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(todayDate: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroid WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>
}