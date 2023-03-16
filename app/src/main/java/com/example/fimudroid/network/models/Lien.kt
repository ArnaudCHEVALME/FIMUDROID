package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class Lien(
    @field:Json(name = "lien") val lien: String
)