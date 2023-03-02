package com.example.fimudroid.models

import com.squareup.moshi.Json

data class Notification(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "date_envoi") val date_envoi: String,
    @field:Json(name = "heure_envoi") val heure_envoi: String,
    @field:Json(name = "message") val message: String
)