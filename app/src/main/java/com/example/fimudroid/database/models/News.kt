package com.example.fimudroid.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fimudroid.database.models.TypeNews

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val news_id: Int,
    val contenu: String?,
    val date_envoi: String?,
    val id_type_news: Int,
    val titre: String?,
)
