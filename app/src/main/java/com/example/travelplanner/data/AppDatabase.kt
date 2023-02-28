package com.example.travelplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelplanner.model.Expenses
import com.example.travelplanner.model.Todo
import com.example.travelplanner.model.Traveler
import com.example.travelplanner.model.Trip

@Database(entities = [Trip::class, Traveler::class, Todo::class, Expenses::class], version = 7, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TripDao(): TripDao
    abstract fun TravelerDao(): TravelerDao
    abstract fun todoDao(): TodoDao
    abstract fun ExpensesDao(): ExpensesDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        // create database instance
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "appdatabase"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}

