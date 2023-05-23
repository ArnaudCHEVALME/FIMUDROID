package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "concerts")
data class Concert(
    @PrimaryKey val id: Int,
    val annee: Int,
    val date_debut: String,
    val duree: Int,
    val heure_debut: String,
    val id_artiste: Int,
    val id_scene: Int,
    val nb_personnes: Int,
)