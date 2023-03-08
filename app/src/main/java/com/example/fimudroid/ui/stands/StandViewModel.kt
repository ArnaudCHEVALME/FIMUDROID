package com.example.fimudroid.ui.stands

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.database.models.News
import com.example.fimudroid.database.models.Stand

class StandViewModel (application: Application) : AndroidViewModel(application)  {
    private val db = FimuDB.getInstance(application)

    fun getAllStands(): LiveData<List<Stand>> {
        return db.standDao().getAll()
    }
}