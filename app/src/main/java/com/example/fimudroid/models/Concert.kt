package com.example.fimudroid.models

data class Concert(
    val annee: Int,
    val artiste: Artiste,
    val date_debut: String,
    val duree: Int,
    val heure_debut: String,
    val id: Int,
    val id_artiste: Int,
    val id_scene: Int,
    val nb_personnes: Int,
    val saison: Saison,
    val scene: Scene
)