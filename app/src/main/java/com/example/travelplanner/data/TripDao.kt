package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.travelplanner.model.Trip

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAll(): LiveData<List<Trip>>

    @Query("SELECT * FROM trips WHERE name LIKE :name")
    fun findByName(name: String): Trip

    @Insert
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)

    @Delete
    suspend fun delete(trip: Trip)

    @Query("DELETE FROM trips")
    suspend fun deleteAll()
}