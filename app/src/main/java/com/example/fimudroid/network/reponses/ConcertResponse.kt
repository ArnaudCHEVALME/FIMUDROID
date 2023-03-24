package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Concert

data class ConcertResponse(
    val error: Int,
    val data: Concert
)
