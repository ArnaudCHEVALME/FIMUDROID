package com.example.fimudroid.models

data class User(
    val id_role: Int,
    val identifiant: String,
    val mot_de_passe: String,
    val role: Role
)