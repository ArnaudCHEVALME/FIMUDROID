package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Artiste

data class ArtisteListResponse (
    val error: Int,
    val data: List<Artiste>
)