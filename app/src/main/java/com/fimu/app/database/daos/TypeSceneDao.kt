package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.TypeScene

@Dao
interface TypeSceneDao {
    @Query("SELECT * FROM types_scene")
    fun getAll(): List<TypeScene>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(types_scene: List<TypeScene>)
}
