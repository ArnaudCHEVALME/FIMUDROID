package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Concert(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "anne") val annee: Int,
    @field:Json(name = "artiste") val artiste: Artiste,
    @field:Json(name = "date_debut") val date_debut: String,
    @field:Json(name = "duree") val duree: Int,
    @field:Json(name = "heure_debut") val heure_debut: String,
    @field:Json(name = "id_artiste") val id_artiste: Int,
    @field:Json(name = "id_scene") val id_scene: Int,
    @field:Json(name = "nb_personnes") val nb_personnes: Int,
    @field:Json(name = "saison") val saison: Saison,
    @field:Json(name = "scene") val scene: Scene
)