package com.udacity.asteroidradar.api


import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

val nasaApiKey = BuildConfig.NASA_API_KEY

interface AsteroidService {
//        BuildConfig.NASA
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidService(
        @Query("start_date") startDate:String ,
        @Query("end_date") endDate:String ,
        @Query("api_key") apiKey:String = nasaApiKey,
    ):ResponseBody

    @GET("planetary/apod")
    suspend fun getPictureOfTheDayService(@Query("api_key") myKey:String = nasaApiKey):PictureOfDay
}