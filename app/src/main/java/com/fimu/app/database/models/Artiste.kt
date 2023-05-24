package com.fimu.app.database.models

import androidx.room.*

@Entity(tableName = "artistes")
data class Artiste(
    @PrimaryKey
    val id: Int,
    val biographie: String,
    val lien_site: String?,
    val lien_video: String?,
    val nom: String,
    val photo: String
)


