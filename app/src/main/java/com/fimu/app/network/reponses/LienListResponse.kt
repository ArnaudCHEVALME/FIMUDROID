package com.fimu.app.network.reponses

import com.fimu.app.network.models.Lien

data class LienListResponse(
    val error: Int,
    val data : List<Lien>
)
