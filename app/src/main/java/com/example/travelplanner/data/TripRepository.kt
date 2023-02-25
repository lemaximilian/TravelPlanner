package com.example.travelplanner.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.travelplanner.model.Trip
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "trip_datastore")

class TripRepository(private val context: Context) {

    companion object {
        val name = stringPreferencesKey("NAME")
    }

    suspend fun saveTrip(trip: Trip) {
        context.dataStore.edit { savedTrip ->
            savedTrip[name] = trip.name
        }
    }

    fun getTrip() = context.dataStore.data
        .map { trip ->
            trip[name] ?: ""
        }

}