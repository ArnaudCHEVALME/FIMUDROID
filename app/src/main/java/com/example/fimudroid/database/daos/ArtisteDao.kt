package com.example.fimudroid.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Artiste

// Define the DAOs
@Dao
interface ArtisteDao {
    @Query("SELECT * FROM artistes")
    fun getAll(): LiveData<List<Artiste>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artistes: List<Artiste>)
}