package com.example.travelplanner.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.TripRepository
import com.example.travelplanner.model.Trip
import com.example.travelplanner.model.TripList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

class MainViewModel(app: Application): AndroidViewModel(app) {

    class Factory(private val app: Application): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(app) as T
        }
    }

    val context = app
    val readAllData: LiveData<List<Trip>>
    private val tripRepository: TripRepository
    private val tripList = TripList()

    init {
        val tripDao = AppDatabase.getInstance(app).TripDao()
        tripRepository = TripRepository(tripDao)
        readAllData = tripRepository.readAllData
    }

    val USERNAME = stringPreferencesKey("username")

    val getUserName: Flow<String> = context.dataStore.data
        .map { preferences ->
           preferences[USERNAME] ?: ""
        }

    suspend fun setUserName(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    fun getTripByID(id: UUID) = tripRepository.getTripByID(id)

    fun addTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.addTrip(trip)
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.updateTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.deleteTrip(trip)
        }
    }

    fun deleteAllTrips() {
        viewModelScope.launch(Dispatchers.IO) {
            tripRepository.deleteAllTrips()
        }
    }

    fun getTripList() = tripList.getAll()
        .map {
            it.toMutableStateList()
        }

}
