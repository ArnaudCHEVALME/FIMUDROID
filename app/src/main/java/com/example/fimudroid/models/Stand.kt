package com.example.fimudroid.models

data class Stand(
    val id: Int,
    val id_typestand: Int,
    val latitude: String,
    val libelle: String,
    val longitude: String,
    val services: List<Service>,
    val typestand: TypeStand
)