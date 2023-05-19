package com.fimu.app.network.reponses

import com.fimu.app.network.models.News

data class NewsListResponse(
    val error: Int,
    val data : List<News>
)
