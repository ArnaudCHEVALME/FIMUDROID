package com.example.fimudroid.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fimudroid.database.models.News
import com.example.fimudroid.database.models.TypeNews

data class NewsWithTypes(
    @Embedded val news: News,
    @Relation(
        parentColumn = "news_id",
        entityColumn = "id_type_news"
    )
    val type_news: TypeNews
)