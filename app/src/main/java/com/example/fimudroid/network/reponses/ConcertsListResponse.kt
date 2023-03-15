package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Concert

data class ConcertsListResponse(
    val error: Int,
    val data : List<Concert>
)
