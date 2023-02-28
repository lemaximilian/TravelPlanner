package com.example.travelplanner.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@Entity(
    tableName = "travelers",
    foreignKeys = [ForeignKey(entity = Trip::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("tripID"),
    onDelete = ForeignKey.CASCADE)]
)
data class Traveler(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey
    val id: UUID,

    // foreignkey, specifying the trip
    @ColumnInfo(name = "tripID")
    val tripID: UUID,

    @ColumnInfo(name = "name")
    val name: String
)