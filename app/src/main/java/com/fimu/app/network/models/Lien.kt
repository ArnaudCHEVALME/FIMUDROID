package com.fimu.app.network.models

import com.squareup.moshi.Json


data class Lien(
    @field:Json(name = "lien") val lien: String
)