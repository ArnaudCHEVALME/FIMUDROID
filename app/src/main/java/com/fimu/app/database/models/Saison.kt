package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saisons")
data class Saison(
    @PrimaryKey
    val annee: Int,
    val couleur1: String,
    val couleur2: String
)