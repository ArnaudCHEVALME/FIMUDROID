package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.News

data class NewsListResponse(
    val error: Int,
    val data : List<News>
)
