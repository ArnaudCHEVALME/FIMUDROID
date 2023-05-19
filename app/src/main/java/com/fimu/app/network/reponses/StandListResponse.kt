package com.fimu.app.network.reponses

import com.fimu.app.network.models.Stand

data class StandListResponse(
    val error: Int,
    val data : List<Stand>
)
