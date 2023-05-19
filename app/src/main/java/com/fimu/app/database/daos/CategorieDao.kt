package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Categorie

// Define the DAOs
@Dao
interface CategorieDao {
    @Query("SELECT * FROM categories")
    fun getAll(): List<Categorie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Categorie>)
}