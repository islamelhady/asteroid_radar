package com.elhady.asteroidradar.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elhady.asteroidradar.Constants
import com.elhady.asteroidradar.model.PictureOfDay
import com.elhady.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidRepository = AsteroidRepository()

    private val _picOfDay = MutableLiveData<PictureOfDay>()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    init {
        getPicOfDay()
    }

    private fun getPicOfDay() {
        viewModelScope.launch {
            try {
                val picture = asteroidRepository.getPicOfDay(Constants.API_KEY)
                picture.let {
                    _picOfDay.value = picture
                }
            } catch (e: Exception) {
            }
        }
    }
}