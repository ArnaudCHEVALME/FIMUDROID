package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Categorie(
    @PrimaryKey
    val id: Int,
    val couleur: String,
    val libelle: String
)