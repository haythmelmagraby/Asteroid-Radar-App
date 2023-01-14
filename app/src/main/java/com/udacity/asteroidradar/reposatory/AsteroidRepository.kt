package com.udacity.asteroidradar.reposatory

import android.util.Log

import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.main.AsteroidApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

class AsteroidRepository(private val database: AsteroidDatabase) {
    fun getDateNow(afterDays:Long=0):String{
        return LocalDate.now().plusDays(afterDays).toString()
    }
    suspend fun refreshData() : AsteroidApiStatus{
        withContext(Dispatchers.IO){
            try {
                val response = AsteroidApi.retrofitService
                    .getAsteroidService(getDateNow(), getDateNow(7))

                val asteroids = parseAsteroidsJsonResult(JSONObject(response.string().toString()))
                database.asteroidDataBaseDao.insertAll(*asteroids.asDatabaseModel())
//                for (i in asteroids)
//                Log.i("AsteroidRepository","Sucess set the data to database" + i.toString())
            }catch (e:Exception){
//                Log.i("AsteroidRepository","Error get Asteroids"+e.toString())
            }
        }
        return AsteroidApiStatus.DONE
    }





}