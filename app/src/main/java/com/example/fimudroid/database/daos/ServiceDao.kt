package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Service

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAll(): List<Service>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(services: List<Service>)
}
