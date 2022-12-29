package com.elhady.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.api.AsteroidApi
import com.elhady.asteroidradar.local.AppDatabase
import com.elhady.asteroidradar.model.Asteroid
import com.elhady.asteroidradar.model.PictureOfDay
import com.elhady.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber.d
import timber.log.Timber.i

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //    private val asteroidRepository = AsteroidRepository()
//    private val database = AppDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(AppDatabase.getInstance(application))



    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _asteroid = asteroidRepository.listAsteroidLiveData
    val asteroid: LiveData<List<Asteroid>>
        get() = _asteroid


    init {
        getAsteroidRadar()
    }


    private fun getPicOfDay() {
        viewModelScope.launch() {
            try {
                val picture = AsteroidApi.retrofitService.getPicOfDay(Constants.API_KEY)
                picture.let {
                    _picOfDay.value = picture
                    i("ipicture ${picOfDay}")
                }
            } catch (e: Exception) {
                d("dpicture ${e.message}")
            }
        }
    }

    private fun getAsteroidRadar() {
        val startDate: String = Constants.getToday()
            val endDate: String = Constants.getSevenDaysLater()
        try {
            viewModelScope.launch {
                asteroidRepository.getAllAsteroids(startDate, endDate, Constants.API_KEY)

                d("Success ${asteroid.value!!.size.toString()}")

            }
        }catch (e:Exception){
            i("Erorr ${e.message}")
        }

    }

//    private fun getAllAsteroid() {
//        viewModelScope.launch {
//            val startDate: String = Constants.getToday()
//            val endDate: String = Constants.getSevenDaysLater()
//            try {
//                val asteroid = AsteroidNetwork.retrofitService.getAllAsteroids(startDate,endDate,Constants.API_KEY)
//                val asteroidlist = parseAsteroidsJsonResult(JSONObject(asteroid))
//                val listAsteroid = asteroidlist.asAsteroidEntities()
//                _asteroid.value = listAsteroid
//                d("sizeAsteroid ${asteroid}")
//
//            } catch (e: Exception) {
//                i("iAsteroid${e}")
//                Timber.e(e)
//
//            }
//        }
//    }

//    fun refreshDataFromNetwork() {
////        getPicOfDay()
//        getAllAsteroid()
//    }
}