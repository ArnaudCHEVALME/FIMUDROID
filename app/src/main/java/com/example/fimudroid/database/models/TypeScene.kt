package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_scene")
data class TypeScene(
    @PrimaryKey
    val id: Int,
    val libelle: String
)