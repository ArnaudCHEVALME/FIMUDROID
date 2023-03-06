package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: Int,
    val contenu: String?,
    val date_envoi: String?,
    val id_typeactu: Int,
    val titre: String?,
)
