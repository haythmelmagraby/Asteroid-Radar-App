package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidEntity)

    @Query("SELECT * FROM asteroids_table ORDER BY closeApproachDate ASC")
    fun getAsteroids() : Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids_table where closeApproachDate >= :startDate and closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsInDate(startDate:String,endDate:String) : Flow<List<AsteroidEntity>>





}