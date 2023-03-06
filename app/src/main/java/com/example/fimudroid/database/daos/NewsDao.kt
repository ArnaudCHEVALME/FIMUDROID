package com.example.fimudroid.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fimudroid.database.models.News

// Define the DAOs
@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)
}