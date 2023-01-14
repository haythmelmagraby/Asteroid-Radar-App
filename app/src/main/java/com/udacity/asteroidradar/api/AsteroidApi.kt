package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


val okHttpClient = OkHttpClient()


private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )).client(
        okHttpClient.newBuilder()
            .readTimeout(60,TimeUnit.SECONDS)
            .build()
    )
    .baseUrl(BASE_URL).build()

object AsteroidApi {
    val retrofitService:AsteroidService by lazy {
        retrofit.create(AsteroidService::class.java)
    }
}