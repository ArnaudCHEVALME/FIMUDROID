package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class News(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "titre") val titre: String?,
    @field:Json(name = "contenu") val contenu: String?,
    @field:Json(name = "lienImage") val lienImage: String?,
    @field:Json(name = "dateEnvoi") val dateEnvoi: String?,
    @field:Json(name = "typeactu") val typeactu: TypeNews
)
