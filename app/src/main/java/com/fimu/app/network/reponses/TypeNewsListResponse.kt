package com.fimu.app.network.reponses

import com.fimu.app.network.models.TypeNews

data class TypeNewsListResponse(
    val error: Int,
    val data : List<TypeNews>
)
