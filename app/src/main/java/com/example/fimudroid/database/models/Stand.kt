package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stands")
data class Stand(
    @PrimaryKey
    val id: Int,
    val id_typestand: Int,
    val latitude: String,
    val libelle: String,
    val longitude: String,
//    val services: List<Service>,
//    val typestand: TypeStand
)