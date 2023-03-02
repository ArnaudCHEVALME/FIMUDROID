package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Service(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "libelle") val libelle: String
)