package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Lien(
    @field:Json(name = "lien_cat") val lien_cat: LienCategorie,
    @field:Json(name = "lien") val lien: String
)