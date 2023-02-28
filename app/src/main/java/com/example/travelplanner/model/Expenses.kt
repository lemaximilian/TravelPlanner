package com.example.travelplanner.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tripID"),
        onDelete = ForeignKey.CASCADE)]
)
data class Expenses(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "tripID")
    val tripID: UUID,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "amount")
    val amount: Double,
)