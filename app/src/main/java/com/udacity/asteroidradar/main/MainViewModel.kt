package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabse
import com.udacity.asteroidradar.reposatory.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : ViewModel() {
    private val _status = MutableLiveData<AsteroidApiStatus>()
    val status: LiveData<AsteroidApiStatus>
        get() = _status

    private val database = getDatabse(application)
    private val repository = AsteroidRepository(database)

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    // navigate to selected property
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: MutableLiveData<List<Asteroid>>
        get() = _asteroidList

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    fun setStatusWithDone(){
        _status.value = AsteroidApiStatus.DONE
    }



    init {
        _status.postValue(AsteroidApiStatus.LOADING)
        getSavedAsteroid()
        getPictureOfTheDay()
        viewModelScope.launch {
            _status.postValue(repository.refreshData())
        }    }



    private fun getPictureOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _pictureOfTheDay.postValue(AsteroidApi.retrofitService.getPictureOfTheDayService())
                Log.i("MainViewModel", "Success picture")
            } catch (e: Exception) {
                Log.i("MainViewModel", "faild picture" + e.toString())
            }

        }
    }
        fun getDateNow(afterDays:Long=0):String{
            return LocalDate.now().plusDays(afterDays).toString()
        }
    fun getWeekAsteroid(){
        viewModelScope.launch {

                database.asteroidDataBaseDao.getAsteroidsInDate(getDateNow(),getDateNow(7)).collect {
                    _asteroidList.value = it.asDomainModel()
                }
        }
    }

    fun getTodayAsteroid(){

        viewModelScope.launch {
            database.asteroidDataBaseDao.getAsteroidsInDate(getDateNow(),getDateNow()).collect {
                _asteroidList.value = it.asDomainModel()
            }
        }
    }

    fun getSavedAsteroid(){
                Log.i("MainViewModelbefore", "getsavedcalled")
        viewModelScope.launch {

            database.asteroidDataBaseDao.getAsteroids().collect {
                _asteroidList.value = it.asDomainModel()
            }
        }
    }

}
