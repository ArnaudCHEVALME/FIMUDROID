package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Notification

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications")
    fun getAll(): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(notifications: List<Notification>)
}
