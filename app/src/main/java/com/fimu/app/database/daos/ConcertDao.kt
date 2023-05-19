package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Concert

@Dao
interface ConcertDao {
    @Query("SELECT * FROM concerts")
    fun getAll(): List<Concert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(concerts: List<Concert>)
}