package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Artiste

data class ArtisteResponse(
    val error: Int,
    val data: Artiste
)
