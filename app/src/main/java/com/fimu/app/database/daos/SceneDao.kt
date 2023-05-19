package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Scene

@Dao
interface SceneDao {
    @Query("SELECT * FROM scenes")
    fun getAll(): List<Scene>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(scenes: List<Scene>)
}
