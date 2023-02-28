package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.travelplanner.model.Expenses
import java.util.*

@Dao
interface ExpensesDao {
    @Query("SELECT * FROM expenses")
    fun getAll(): LiveData<List<Expenses>>

    @Query("SELECT * FROM expenses WHERE id LIKE :id")
    fun getExpensesByID(id: UUID): LiveData<Expenses>

    @Query("SELECT * FROM expenses WHERE description LIKE :description")
    fun findByName(description: String): Expenses

    @Insert
    suspend fun insert(expenses: Expenses)

    @Update
    suspend fun update(expenses: Expenses)

    @Delete
    suspend fun delete(expenses: Expenses)

    @Query("DELETE FROM expenses")
    suspend fun deleteAll()
}