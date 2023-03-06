package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Pays

@Dao
interface PaysDao {
    @Query("SELECT * FROM pays")
    fun getAll(): List<Pays>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(pays: List<Pays>)
}
