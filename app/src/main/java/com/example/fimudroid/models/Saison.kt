package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Saison(
    @field:Json(name = "id") val annee: Int,
    @field:Json(name = "couleur1") val couleur1: String,
    @field:Json(name = "couleur1") val couleur2: String
)