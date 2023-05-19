package com.fimu.app.network.reponses

import com.fimu.app.network.models.Concert

data class ConcertResponse(
    val error: Int,
    val data: Concert
)
