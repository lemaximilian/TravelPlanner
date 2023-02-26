package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import com.example.travelplanner.model.Trip

class TripRepository(private val tripDao: TripDao) {

    val readAllData: LiveData<List<Trip>> = tripDao.getAll()

    suspend fun addTrip(trip: Trip) {
        tripDao.insert(trip)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.update(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        tripDao.delete(trip)
    }

    suspend fun deleteAllTrips() {
        tripDao.deleteAll()
    }
}