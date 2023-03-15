package com.example.fimudroid.network.reponses

import com.example.fimudroid.network.models.Scene

data class SceneListResponse(
    val error: Int,
    val data : List<Scene>
)
