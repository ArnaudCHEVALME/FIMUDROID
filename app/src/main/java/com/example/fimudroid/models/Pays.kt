package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Pays(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "libelle") val libelle: String
)