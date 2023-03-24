package com.example.fimudroid.network.models

import com.squareup.moshi.Json


data class Concert(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "heure_debut") val heure_debut: String,
    @field:Json(name = "heure_fin") val heure_fin: String,
    @field:Json(name = "date_debut") val date_debut: String,
    @field:Json(name = "duree") val duree: Int,
    @field:Json(name = "nbPersonnes") val nbPersonnes: Int,
    @field:Json(name = "artisteId") val artisteId: Int,
    @field:Json(name = "sceneId") val sceneId: Int,
    @field:Json(name = "scene") val scene: Scene,
    @field:Json(name = "artiste") val artiste: Artiste?,
)