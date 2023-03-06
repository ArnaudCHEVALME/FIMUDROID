package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class Categorie(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "couleur") val couleur: String,
    @field:Json(name = "libelle") val libelle: String
)