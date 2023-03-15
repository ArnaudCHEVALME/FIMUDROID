package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Lien

data class LienListResponse(
    val error: Int,
    val data : List<Lien>
)
