package com.example.fimudroid.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.database.models.News

class NewsViewModel (application: Application) : AndroidViewModel(application)  {
    private val db = FimuDB.getInstance(application)

    fun getAllNews(): LiveData<List<News>> {
        return db.newsDao().getAll()
    }

    fun getById(id:Int): List<News>{
        return db.newsDao().getById(id)
    }
}
