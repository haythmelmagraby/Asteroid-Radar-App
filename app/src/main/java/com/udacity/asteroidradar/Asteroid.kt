package com.udacity.asteroidradar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
// parcel to share complex object between processes with bundels"and fragments"
//this is my domain
@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable