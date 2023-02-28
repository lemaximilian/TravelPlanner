package com.example.travelplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelplanner.model.Traveler
import com.example.travelplanner.model.Trip

@Database(entities = [Trip::class, Traveler::class], version = 6, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TripDao(): TripDao
    abstract fun TravelerDao(): TravelerDao

    companion object {

        private var INSTANCE: AppDatabase? = null

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