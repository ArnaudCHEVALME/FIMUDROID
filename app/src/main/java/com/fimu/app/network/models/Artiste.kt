package com.fimu.app.network.models

import com.squareup.moshi.Json


data class Artiste(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "biographie") val biographie: String,
    @field:Json(name = "categorie") val categorie: Categorie,
    @field:Json(name = "genres") val genres: List<Genre>?,
    @field:Json(name = "lien_site") val lien_site: String?,
    @field:Json(name = "lien_video") val lien_video: String?,
    @field:Json(name = "nom") val nom: String,
    @field:Json(name = "pays") val pays: List<Pays>?,
    @field:Json(name = "photo") val photo: String?,
    @field:Json(name = "reseauxSociauxes") val reseauxSociauxes: List<ReseauSocial>?,
    @field:Json(name = "concerts") val concerts: List<Concert>?
)