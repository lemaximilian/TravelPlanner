package com.example.travelplanner.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

@Entity(tableName = "trips")
@Serializable
data class Trip(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey
    val id: UUID,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "start")
    val start: String?,

    @ColumnInfo(name = "destination")
    val destination: String?,
)
