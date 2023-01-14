package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDataBaseDao:AsteroidDao
}
private lateinit var INSTANCE : AsteroidDatabase


fun getDatabse(context: Context):AsteroidDatabase{
        synchronized(AsteroidDatabase::class.java){
            if (!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                ).build()
            }
        }
    return INSTANCE

}