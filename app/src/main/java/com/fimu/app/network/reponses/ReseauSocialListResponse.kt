package com.fimu.app.network.reponses

import com.fimu.app.network.models.ReseauSocial

data class ReseauSocialListResponse(
    val error: Int,
    val data: List<ReseauSocial>
)
