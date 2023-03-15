package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Saison

data class SaisonListResponse(
    val error: Int,
    val data : List<Saison>
)
