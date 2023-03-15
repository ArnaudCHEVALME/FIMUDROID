package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.CategorieLien

data class CategorieLienListResponse(
    val error: Int,
    val data : List<CategorieLien>
)
