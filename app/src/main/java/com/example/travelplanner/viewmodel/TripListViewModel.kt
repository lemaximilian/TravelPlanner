package com.example.travelplanner.viewmodel

import androidx.lifecycle.ViewModel
import com.example.travelplanner.model.Trip
import com.example.travelplanner.model.TripList

class TripListViewModel: ViewModel() {
    private val tripList = TripList()

    fun tripList() = tripList.tripList()

    fun getTripList() = tripList.getAll()

    fun addTrip(name: String) {
        tripList.addTrip(Trip(name))
    }
}