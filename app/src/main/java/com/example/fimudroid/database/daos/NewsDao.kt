package com.example.fimudroid.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fimudroid.database.models.News

// Define the DAOs
@Dao
interface NewsDao {
    @Transaction
    @Query("SELECT * FROM news")
    fun getAll(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Transaction
    @Query("SELECT * FROM news WHERE news_id = :id")
    fun getById(id: Int): List<News>
}