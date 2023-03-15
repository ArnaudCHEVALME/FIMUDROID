package com.example.fimudroid.database.models

import androidx.room.*

@Entity
data class News(
    @PrimaryKey val id: Int,
    val contenu: String?,
    val date_envoi: String?,
    val titre: String?,
    @Embedded val type_news : TypeNews
)
