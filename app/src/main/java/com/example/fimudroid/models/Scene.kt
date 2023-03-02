package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Scene(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "id_typescene") val id_typescene: Int,
    @field:Json(name = "jauge") val jauge: Int?,
    @field:Json(name = "latitude") val latitude: String,
    @field:Json(name = "longitude") val longitude: String,
    @field:Json(name = "libelle") val libelle: String,
    @field:Json(name = "typescene") val typescene: TypeScene
)