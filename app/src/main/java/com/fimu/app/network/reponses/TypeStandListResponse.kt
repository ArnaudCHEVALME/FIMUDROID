package com.fimu.app.network.reponses

import com.fimu.app.network.models.TypeStand

data class TypeStandListResponse(
    val error: Int,
    val data : List<TypeStand>
)