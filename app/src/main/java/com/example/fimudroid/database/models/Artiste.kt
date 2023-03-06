package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artistes")
data class Artiste(
    @PrimaryKey
    val id: Int,
    val biographie: String,
    val lien_site: String?,
    val lien_video: String?,
    val nom: String,
    val photo: String,
)