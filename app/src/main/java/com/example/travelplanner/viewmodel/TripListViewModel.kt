package com.example.travelplanner.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.travelplanner.model.Trip
import com.example.travelplanner.model.TripList
import java.util.*

class TripListViewModel(): ViewModel() {
    private val tripList = TripList()

    fun getTripList() = tripList.getAll()
        .map {
            it.toMutableStateList()
        }

    fun addTrip(name: String) {
        tripList.addTrip(Trip(UUID.randomUUID(), name))
    }
    val usernameState = mutableStateOf("")

}
