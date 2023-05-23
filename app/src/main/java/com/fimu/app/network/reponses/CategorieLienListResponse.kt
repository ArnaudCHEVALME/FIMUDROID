package com.fimu.app.network.reponses

import com.fimu.app.network.models.CategorieLien

data class CategorieLienListResponse(
    val error: Int,
    val data : List<CategorieLien>
)
