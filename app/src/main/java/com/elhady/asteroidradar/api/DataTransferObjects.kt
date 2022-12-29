package com.elhady.asteroidradar.api

import com.elhady.asteroidradar.local.DatabaseAsteroid
import com.elhady.asteroidradar.model.Asteroid

/**
 * Convert Network results to database objects
 */
fun ArrayList<Asteroid>.asAsteroidEntities() : List<DatabaseAsteroid> {
    return this.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toList()
}