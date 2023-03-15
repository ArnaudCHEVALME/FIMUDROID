package com.example.fimudroid.network.reponses

import com.example.fimudroid.database.models.TypeScene

data class TypeSceneListResponse(
    val error: Int,
    val data : List<TypeScene>
)
