package com.example.travelplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TripList {
    private val tripList = mutableListOf<Trip>()
    private val tripListLive = MutableLiveData<List<Trip>>()

    init {
        fillWithTestData()
    }

    private fun fillWithTestData() {

    }

    fun addTrip(trip: Trip) {
        tripList += trip
    }

    fun getAll(): LiveData<List<Trip>> = tripListLive
}