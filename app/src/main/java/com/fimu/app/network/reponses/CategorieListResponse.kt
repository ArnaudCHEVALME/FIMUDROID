package com.fimu.app.network.reponses

import com.fimu.app.network.models.Categorie

data class CategorieListResponse(
    val error: Int,
    val data : List<Categorie>
)
