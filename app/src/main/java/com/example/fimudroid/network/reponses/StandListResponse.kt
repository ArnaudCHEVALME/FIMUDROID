package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Stand

data class StandListResponse(
    val error: Int,
    val data : List<Stand>
)
