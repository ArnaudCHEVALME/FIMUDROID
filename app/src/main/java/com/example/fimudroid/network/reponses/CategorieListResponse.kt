package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Categorie

data class CategorieListResponse(
    val error: Int,
    val data : List<Categorie>
)
