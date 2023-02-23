package com.example.fimudroid.models

data class Actualite(
    val contenu: String,
    val date_envoi: Any,
    val id: Int,
    val id_typeactu: Int,
    val titre: String,
    val typeactu: TypeActu
)