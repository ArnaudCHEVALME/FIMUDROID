package com.fimu.app.network.models

import com.squareup.moshi.Json


data class Pays(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "libelle") val libelle: String
)