package com.fimu.app.network.reponses

import com.fimu.app.network.models.Pays

data class PaysListResponse(
    val error: Int,
    val data : List<Pays>
)
