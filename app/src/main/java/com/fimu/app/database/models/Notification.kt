package com.fimu.app.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey
    val id: Int,
    val date_envoi: String,
    val heure_envoi: String,
    val message: String
)