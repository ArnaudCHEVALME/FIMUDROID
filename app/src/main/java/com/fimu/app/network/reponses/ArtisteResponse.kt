package com.fimu.app.network.reponses

import com.fimu.app.network.models.Artiste

data class ArtisteResponse(
    val error: Int,
    val data: Artiste
)
