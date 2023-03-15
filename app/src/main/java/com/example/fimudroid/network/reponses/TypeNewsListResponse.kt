package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.TypeNews

data class TypeNewsListResponse(
    val error: Int,
    val data : List<TypeNews>
)
