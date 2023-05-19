package com.fimu.app.network.reponses

import com.fimu.app.database.models.Service

data class ServiceListResponse(
    val error: Int,
    val data : List<Service>
)
