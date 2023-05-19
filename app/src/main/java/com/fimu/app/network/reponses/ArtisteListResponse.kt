package com.fimu.app.network.reponses

import com.fimu.app.network.models.Artiste

data class ArtisteListResponse (
    val error: Int,
    val data: List<Artiste>
)