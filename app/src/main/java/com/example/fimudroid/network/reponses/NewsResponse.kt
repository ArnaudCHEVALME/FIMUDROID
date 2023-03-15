package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.News

data class NewsResponse(
    val error: Int,
    val data: News
)
