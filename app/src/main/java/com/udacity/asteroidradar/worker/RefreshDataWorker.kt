package com.udacity.asteroidradar.worker

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabse
import com.udacity.asteroidradar.reposatory.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context,params:WorkerParameters):
    CoroutineWorker(appContext,params) {

    override suspend fun doWork(): Result {
        val database = getDatabse(applicationContext)
        val repository = AsteroidRepository(database)
        repository.refreshData()
        return try {
            repository.refreshData()
            Log.i("RefreshDataWorker","worker success")
            Result.success()
        }catch (e:HttpException){
            Log.i("RefreshDataWorker","worker faild")
            Result.retry()
        }
    }
}