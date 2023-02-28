package com.example.travelplanner.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.TravelerRepository
import com.example.travelplanner.model.Traveler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TravelerViewModel(app: Application): AndroidViewModel(app) {

    class Factory(private val app: Application): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TravelerViewModel(app) as T
        }
    }

    val context = app
    val readAllData: LiveData<List<Traveler>>
    private val travelerRepository: TravelerRepository

    init {
        val travelerDao = AppDatabase.getInstance(app).TravelerDao()
        travelerRepository = TravelerRepository(travelerDao)
        readAllData = travelerRepository.readAllData
    }

    // Traveler
    fun getTravelerByID(id: UUID) = travelerRepository.getTravelerByID(id)

    fun addTraveler(traveler: Traveler) {
        viewModelScope.launch(Dispatchers.IO) {
            travelerRepository.addTraveler(traveler)
        }
    }

    fun updateTraveler(traveler: Traveler) {
        viewModelScope.launch(Dispatchers.IO) {
            travelerRepository.updateTraveler(traveler)
        }
    }

    fun deleteTraveler(traveler: Traveler) {
        viewModelScope.launch(Dispatchers.IO) {
            travelerRepository.deleteTraveler(traveler)
        }
    }

    fun deleteAllTravelers() {
        viewModelScope.launch(Dispatchers.IO) {
            travelerRepository.deleteAllTravelers()
        }
    }
}