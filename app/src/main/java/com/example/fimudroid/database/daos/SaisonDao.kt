package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Saison

@Dao
interface SaisonDao {
    @Query("SELECT * FROM saisons")
    fun getAll(): List<Saison>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(saisons: List<Saison>)
}
