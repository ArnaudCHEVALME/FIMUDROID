package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Concert

@Dao
interface ConcertDao {
    @Query("SELECT * FROM concerts")
    fun getAll(): List<Concert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(concerts: List<Concert>)
}