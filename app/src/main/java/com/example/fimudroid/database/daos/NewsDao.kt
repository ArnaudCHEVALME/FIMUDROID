package com.example.fimudroid.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fimudroid.database.models.News

// Define the DAOs
@Dao
interface NewsDao {
    @Transaction
    @Query("SELECT * FROM news JOIN types_news ON news.id_type_news = news.id_type_news")
    fun getAll(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)
}