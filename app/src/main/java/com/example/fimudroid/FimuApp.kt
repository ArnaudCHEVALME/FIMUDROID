package com.example.fimudroid

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.fimudroid.network.models.Artiste as ArtisteNet
import com.example.fimudroid.database.models.Artiste as ArtisteDB
import com.example.fimudroid.network.models.News as NewsNet
import com.example.fimudroid.database.models.News as NewsDB
import com.example.fimudroid.network.models.TypeNews as TypeNewsNet
import com.example.fimudroid.database.models.TypeNews as TypeNewsDB
import com.example.fimudroid.database.models.Stand as StandDB
import com.example.fimudroid.network.models.Stand as StandNet

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

        val context: Context = applicationContext // TODO - Remove for release
        val deleted = context.deleteDatabase("fimu-db") // TODO - Remove for release

// Check if the database was deleted
        if (deleted) {
            Log.i("DB", "Database deleted")
        } else {
            Log.i("DB", "Failed to delete database")
        }



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
            val typesNews = service.getTypesNews()

            // Convert network models to database models
            val artisteDBs = artistes.map { it.toArtistDB() }
            val newsDBs = news.map { it.toNewsDB() }
            val typesNewsDBs = typesNews.map {it.toTypeNewsDB()}
            val standsDBs = stands.map { it.toStandDB() }

            // Store data in Room database
            db.artisteDao().insertAll(artisteDBs)
            db.typeNewsDao().inserAll(typesNewsDBs)
            db.newsDao().insertAll(newsDBs)
            db.standDao().inserAll(standsDBs)

            val news2 = db.newsDao().getAll()
            Log.i("RELA : ", news2.toString())
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
    fun TypeNewsNet.toTypeNewsDB(): TypeNewsDB {
        return TypeNewsDB(
            id, libelle
        )
    }

    fun StandNet.toStandDB(): StandDB{
        return StandDB(
            id, id_typestand, latitude, libelle, longitude
        )
    }

}