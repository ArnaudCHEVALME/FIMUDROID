package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.CategorieLien

@Dao
interface CategorieLienDao {
    @Query("SELECT * FROM categories_lien")
    fun getAll(): List<CategorieLien>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories_liens: List<CategorieLien>)
}
