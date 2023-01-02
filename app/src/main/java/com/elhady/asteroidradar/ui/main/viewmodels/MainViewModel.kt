package com.elhady.asteroidradar.ui.main.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.api.AsteroidApi
import com.elhady.asteroidradar.local.AppDatabase
import com.elhady.asteroidradar.local.asDomainModel
import com.elhady.asteroidradar.model.Asteroid
import com.elhady.asteroidradar.model.PictureOfDay
import com.elhady.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber.i

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidRepository = AsteroidRepository(AppDatabase.getDatabase(application))


    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _asteroid = asteroidRepository.listAsteroidLiveData
    val asteroid: LiveData<List<Asteroid>>
        get() = _asteroid


    init {
        getAsteroidRadar()
        getPicOfDay()
    }


    private fun getPicOfDay() {
        viewModelScope.launch() {
            try {
                val picture = AsteroidApi.retrofitService.getPicOfDay(Constants.API_KEY)
                picture.let {
                    _picOfDay.value = picture
                    i("Success: ${picture.title} Picture of Day properties retrieved")
                }
            } catch (e: Exception) {
                i("Failure: ${e.message}")
            }
        }
    }

    private fun getAsteroidRadar() {
        val startDate: String = Constants.getToday()
        val endDate: String = Constants.getSevenDaysLater()
        try {
            viewModelScope.launch {
                asteroidRepository.getAllAsteroids(startDate, endDate, Constants.API_KEY)
                i("Success ${asteroid.value!!.size}")
            }
        } catch (e: Exception) {
            i("Failure ${e.message}")
        }
    }

    fun getTodayAsteroids(): LiveData<List<Asteroid>> =
        Transformations.map(asteroidRepository.getTodayAsteroids(Constants.getToday())){
            it.asDomainModel()
        }

    fun getWeekAsteroids(): LiveData<List<Asteroid>> =
        Transformations.map(asteroidRepository.getWeekAsteroids(Constants.getToday(),Constants.getToday())){
            it.asDomainModel()
        }

}