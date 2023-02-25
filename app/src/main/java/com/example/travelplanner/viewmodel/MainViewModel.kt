package com.example.travelplanner.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.travelplanner.model.Trip
import com.example.travelplanner.model.TripList

class MainViewModel: ViewModel() {
    private val tripList = TripList()

    fun getTripList() = tripList.getAll()
        .map {
            it.toMutableStateList()
        }

    fun addTrip(name: String) {
        tripList.addTrip(Trip(name))
    }

}