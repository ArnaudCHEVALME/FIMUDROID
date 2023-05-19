package com.fimu.app.network.reponses

import com.fimu.app.network.models.Concert

data class ConcertsListResponse(
    val error: Int,
    val data : List<Concert>
)
