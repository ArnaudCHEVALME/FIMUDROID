package com.example.fimudroid.models

data class Artiste(
    val id: Int,
    val biographie: String,
    val category: Categorie,
    val genres: List<Genre>,
    val lien_site: String,
    val lien_video: String,
    val nom: String,
    val pays: List<Pays>,
    val photo: String,
    val liens: List<Lien>
)