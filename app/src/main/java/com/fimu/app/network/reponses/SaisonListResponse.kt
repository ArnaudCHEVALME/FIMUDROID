package com.fimu.app.network.reponses

import com.fimu.app.network.models.Saison

data class SaisonListResponse(
    val error: Int,
    val data : List<Saison>
)
