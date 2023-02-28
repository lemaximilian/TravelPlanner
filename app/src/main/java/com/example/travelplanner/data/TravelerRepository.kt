package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import com.example.travelplanner.model.Traveler
import java.util.*

class TravelerRepository(private val travelerDao: TravelerDao) {

    val readAllData: LiveData<List<Traveler>> = travelerDao.getAll()

    fun getTravelerByID(id: UUID): LiveData<Traveler> = travelerDao.getTravelerByID(id)

    suspend fun addTraveler(traveler: Traveler) {
        travelerDao.insert(traveler)
    }

    suspend fun updateTraveler(traveler: Traveler) {
        travelerDao.update(traveler)
    }

    suspend fun deleteTraveler(traveler: Traveler) {
        travelerDao.delete(traveler)
    }

    suspend fun deleteAllTravelers() {
        travelerDao.deleteAll()
    }
}