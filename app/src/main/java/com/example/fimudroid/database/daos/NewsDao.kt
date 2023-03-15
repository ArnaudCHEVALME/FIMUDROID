package com.example.fimudroid.database.daos

import NewsWithType
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fimudroid.database.models.News

// Define the DAOs
@Dao
interface NewsDao {
    @Transaction
    @Query("SELECT * FROM news INNER JOIN types_news ON news.id_type_news=types_news.id_type_news")
    fun getAll(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Transaction
    @Query("SELECT * FROM news JOIN types_news ON news.id_type_news=types_news.id_type_news WHERE id=:id")
    fun getById(id: Int): LiveData<News>
}