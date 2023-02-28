package com.example.travelplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelplanner.model.Todo
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

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null
        fun getDatabase(context: Context): TodoRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java,
                    "todo_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}