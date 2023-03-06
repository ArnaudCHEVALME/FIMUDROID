package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class TypeScene(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "libelle") val libelle: String
)