package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.TypeStand

data class TypeStandListResponse(
    val error: Int,
    val data : List<TypeStand>
)