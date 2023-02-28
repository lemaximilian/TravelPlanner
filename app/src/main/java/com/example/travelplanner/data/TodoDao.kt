package com.example.travelplanner.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.travelplanner.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getAll(): LiveData<List<Todo>>

    @Insert
    fun insertAll(vararg todoItems: Todo)

    @Delete
    fun delete(todoItem: Todo)

    @Update
    fun update(todoItem: Todo)
}