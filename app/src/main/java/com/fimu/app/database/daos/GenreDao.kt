package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)
}
