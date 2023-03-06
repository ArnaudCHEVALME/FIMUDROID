package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pays")
data class Pays(
    @PrimaryKey
    val id: Int,
    val libelle: String
)