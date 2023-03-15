package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class Stand(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "typestandId") val typestandId: Int,
    @field:Json(name = "latitude") val latitude: String,
    @field:Json(name = "libelle") val libelle: String,
    @field:Json(name = "longitude") val longitude: String,
    @field:Json(name = "services") val services: List<Service>,
    @field:Json(name = "typestand") val typestand: TypeStand
)