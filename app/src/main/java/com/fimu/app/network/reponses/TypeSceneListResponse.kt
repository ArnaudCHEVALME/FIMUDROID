package com.fimu.app.network.reponses

import com.fimu.app.database.models.TypeScene

data class TypeSceneListResponse(
    val error: Int,
    val data : List<TypeScene>
)
