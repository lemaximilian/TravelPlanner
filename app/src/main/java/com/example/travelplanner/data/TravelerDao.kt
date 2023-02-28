package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.travelplanner.model.Traveler
import java.util.*

@Dao
interface TravelerDao {
    @Query("SELECT * FROM travelers")
    fun getAll(): LiveData<List<Traveler>>

    @Query("SELECT * FROM travelers WHERE id LIKE :id")
    fun getTravelerByID(id: UUID): LiveData<Traveler>

    @Query("SELECT * FROM travelers WHERE name LIKE :name")
    fun findByName(name: String): Traveler

    @Insert
    suspend fun insert(traveler: Traveler)

    @Update
    suspend fun update(traveler: Traveler)

    @Delete
    suspend fun delete(traveler: Traveler)

    @Query("DELETE FROM travelers")
    suspend fun deleteAll()
}