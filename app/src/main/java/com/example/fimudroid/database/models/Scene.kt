package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scenes")
data class Scene(
    @PrimaryKey
    val id: Int,
    val id_typescene: Int,
    val jauge: Int?,
    val latitude: String,
    val longitude: String,
    val libelle: String,
//    val typescene: TypeScene
)