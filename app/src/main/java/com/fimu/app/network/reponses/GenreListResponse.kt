package com.fimu.app.network.reponses

import com.fimu.app.network.models.Genre

data class GenreListResponse(
    val error: Int,
    val data : List<Genre>
)
