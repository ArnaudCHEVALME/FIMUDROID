package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.TypeStand

@Dao
interface TypeStandDao {
    @Query("SELECT * FROM types_stand")
    fun getAll(): List<TypeStand>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(types_stand: List<TypeStand>)
}
