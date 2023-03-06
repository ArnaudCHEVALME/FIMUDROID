package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Notification

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications")
    fun getAll(): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(notifications: List<Notification>)
}
