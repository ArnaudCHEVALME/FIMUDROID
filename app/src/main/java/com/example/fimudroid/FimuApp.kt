package com.example.fimudroid

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.fimudroid.database.models.Artiste as ArtisteDB
import com.example.fimudroid.database.models.News as NewsDB
import com.example.fimudroid.network.models.Artiste as ArtisteNet
import com.example.fimudroid.network.models.News as NewsNet

class FimuApp : Application() {
    // Initialize Retrofit service
    private val service: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    // Initialize Room database
    private val db: FimuDB by lazy {
        Room.databaseBuilder(applicationContext, FimuDB::class.java, "fimu-db").build()
    }

    override fun onCreate() {
        super.onCreate()

        // Make API call to fetch data
        CoroutineScope(Dispatchers.IO).launch {
            val artistes = service.getArtistes()
            val categories = service.getCategories()
            val pays = service.getPays()
            val genres = service.getGenres()
            val news = service.getNews()
            val stands = service.getStands()
            val services = service.getServices()
            val typeStand = service.getTypesStand()

            Log.i("DATA", artistes.toString())


            // Convert network models to database models
            val artisteDBs = artistes.map { it.toArtistDB() }
            val newsDBs = news.map { it.toNewsDB() }

            // Store data in Room database
            db.artisteDao().insertAll(artisteDBs)
            db.newsDao().insertAll(newsDBs)
        }
    }

    fun ArtisteNet.toArtistDB(): ArtisteDB {
        return ArtisteDB(
            id, biographie, lien_site, lien_video, nom, photo
        )
    }

    fun NewsNet.toNewsDB(): NewsDB {
        return NewsDB(
            id, contenu, date_envoi, id_typeactu, titre
        )
    }
}