package com.example.fimudroid.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.Categorie

// Define the DAOs
@Dao
interface CategorieDao {
    @Query("SELECT * FROM categories")
    fun getAll(): List<Categorie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Categorie>)
}