package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Stand

@Dao
interface StandDao {
    @Query("SELECT * FROM stands")
    fun getAll(): List<Stand>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(stands: List<Stand>)
}
