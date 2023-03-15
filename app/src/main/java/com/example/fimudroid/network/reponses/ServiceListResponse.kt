package com.example.fimudroid.network.reponses

import com.example.fimudroid.database.models.Service

data class ServiceListResponse(
    val error: Int,
    val data : List<Service>
)
