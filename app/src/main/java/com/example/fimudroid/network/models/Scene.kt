package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class Scene(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "typesceneId") val typesceneId: Int,
    @field:Json(name = "jauge") val jauge: Int?,
    @field:Json(name = "latitude") val latitude: String,
    @field:Json(name = "longitude") val longitude: String,
    @field:Json(name = "libelle") val libelle: String,
    @field:Json(name = "typescene") val typescene: TypeScene
)