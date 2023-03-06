package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types_news")
data class TypeNews(
    @PrimaryKey
    val id_news: Int,
    val libelle: String
)