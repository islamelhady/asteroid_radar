package com.elhady.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.api.AsteroidNetwork
import com.elhady.asteroidradar.api.asAsteroidEntities
import com.elhady.asteroidradar.api.parseAsteroidsJsonResult
import com.elhady.asteroidradar.model.Asteroid
import com.elhady.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import timber.log.Timber
import timber.log.Timber.d
import timber.log.Timber.i

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val asteroidRepository = AsteroidRepository()


    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _asteroid = MutableLiveData<List<Asteroid>>()
    val asteroid: LiveData<List<Asteroid>>
        get() = _asteroid


    init {
        refreshDataFromNetwork()
    }



    private fun getPicOfDay() {
        viewModelScope.launch() {
            try {
                val picture =  AsteroidNetwork.retrofitService.getPicOfDay(Constants.API_KEY)
                picture.let {
                    _picOfDay.value = picture
                    Timber.i("ipicture ${picOfDay}")
                }
            } catch (e: Exception) {
                d("dpicture ${e.message}")
            }
        }
    }

    private fun getAllAsteroid() {
        viewModelScope.launch {
            val startDate: String = Constants.getToday()
            val endDate: String = Constants.getSevenDaysLater()
            try {
                val asteroid = AsteroidNetwork.retrofitService.getAllAsteroids(startDate,endDate,Constants.API_KEY)
                val asteroidlist = parseAsteroidsJsonResult(JSONObject(asteroid))
                val listAsteroid = asteroidlist.asAsteroidEntities()
                _asteroid.value = listAsteroid
                d("sizeAsteroid ${asteroid}")

            } catch (e: Exception) {
                i("iAsteroid${e}")
                Timber.e(e)

            }
        }
    }

    fun refreshDataFromNetwork(){
//        getPicOfDay()
        getAllAsteroid()
    }
}