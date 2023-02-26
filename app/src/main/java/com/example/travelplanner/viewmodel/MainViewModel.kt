package com.example.travelplanner.viewmodel

import android.app.Application
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.TripRepository
import com.example.travelplanner.model.Trip
import com.example.travelplanner.model.TripList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(app: Application): AndroidViewModel(app) {

    class Factory(private val app: Application): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(app) as T
        }
    }

    val readAllData: LiveData<List<Trip>>
    private val repository: TripRepository
    private val tripList = TripList()

    init {
        val tripDao = AppDatabase.getInstance(app).TripDao()
        repository = TripRepository(tripDao)
        readAllData = repository.readAllData
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTrip(trip)
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrip(trip)
        }
    }

    fun deleteAllTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTrips()
        }
    }

    fun getTripList() = tripList.getAll()
        .map {
            it.toMutableStateList()
        }

    fun addTrip(name: String) {
        tripList.addTrip(Trip(UUID.randomUUID(), name))
    }

}
