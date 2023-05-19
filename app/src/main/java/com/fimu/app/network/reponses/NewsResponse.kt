package com.fimu.app.network.reponses

import com.fimu.app.network.models.News

data class NewsResponse(
    val error: Int,
    val data: News
)
