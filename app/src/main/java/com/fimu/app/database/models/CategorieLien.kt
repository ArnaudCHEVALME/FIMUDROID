package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_lien")
data class CategorieLien(
    @PrimaryKey
    val id: Int,
    val libelle: String,
    val logo: String
)