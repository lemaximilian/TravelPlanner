package com.example.travelplanner.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.UUID

class TripList {
    private val tripList = mutableListOf<Trip>()
    private val tripListLive = MutableLiveData<List<Trip>>()

    init {
        fillWithTestData()
    }

    private fun fillWithTestData() {

    }

    fun tripList() = tripList

    fun addTrip(trip: Trip) {
        tripList += trip
        tripListLive.postValue(tripList)
    }

    fun getTrip(id: UUID): Trip? {
        return tripList.find { it.id == id }
    }

    fun getAll(): LiveData<List<Trip>> = tripListLive
}