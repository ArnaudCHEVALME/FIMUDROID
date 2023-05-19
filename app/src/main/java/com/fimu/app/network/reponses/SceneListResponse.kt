package com.fimu.app.network.reponses

import com.fimu.app.network.models.Scene

data class SceneListResponse(
    val error: Int,
    val data : List<Scene>
)
