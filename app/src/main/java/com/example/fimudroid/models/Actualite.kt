package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Actualite(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "contenu") val contenu: String?,
    @field:Json(name = "date_envoi") val date_envoi: String?,
    @field:Json(name = "id_typeactu") val id_typeactu: Int,
    @field:Json(name = "titre") val titre: String?,
    @field:Json(name = "typeactu") val typeactu: TypeActu
)