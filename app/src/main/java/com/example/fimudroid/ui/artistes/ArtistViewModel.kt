package com.example.fimudroid.ui.artistes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fimudroid.database.models.Artiste
import com.example.fimudroid.database.FimuDB

class ArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FimuDB.getInstance(application)

    fun getAllArtists(): LiveData<List<Artiste>> {
        return db.artisteDao().getAll()
    }

    fun getById(id: Int): List<Artiste>{
        return db.artisteDao().getById(id)
    }
}