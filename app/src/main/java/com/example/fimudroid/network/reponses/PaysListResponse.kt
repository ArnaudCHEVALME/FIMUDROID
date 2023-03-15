package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Pays

data class PaysListResponse(
    val error: Int,
    val data : List<Pays>
)
