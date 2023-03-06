package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Scene

@Dao
interface SceneDao {
    @Query("SELECT * FROM scenes")
    fun getAll(): List<Scene>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(scenes: List<Scene>)
}
