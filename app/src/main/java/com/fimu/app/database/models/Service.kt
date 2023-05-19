package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
    @PrimaryKey
    val id: Int,
    val libelle: String
)