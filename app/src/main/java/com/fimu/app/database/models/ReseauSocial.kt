package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reseaux_sociaux")
data class ReseauSocial(
    @PrimaryKey
    val id: Int,
    val libelle: String,
    val logo: String
)