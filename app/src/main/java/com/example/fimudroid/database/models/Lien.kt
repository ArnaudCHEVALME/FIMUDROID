package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liens")
data class Lien(
    @PrimaryKey
    val id_artiste: Int,
    val id_reseaux_sociaux: Int,
    val lien: String
)