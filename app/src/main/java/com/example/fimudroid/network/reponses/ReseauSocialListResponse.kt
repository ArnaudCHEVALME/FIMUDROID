package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.ReseauSocial

data class ReseauSocialListResponse(
    val error: Int,
    val data: List<ReseauSocial>
)
