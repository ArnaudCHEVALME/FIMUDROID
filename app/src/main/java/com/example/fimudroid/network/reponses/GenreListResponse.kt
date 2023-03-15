package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Genre

data class GenreListResponse(
    val error: Int,
    val data : List<Genre>
)
