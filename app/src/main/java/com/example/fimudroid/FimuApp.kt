package com.example.fimudroid

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess
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


//    // Initialize Room database
//    private val db: FimuDB by lazy {
//        Room.databaseBuilder(applicationContext, FimuDB::class.java, "fimu-db").build()
//    }

    override fun onCreate() {
        super.onCreate()
    }

}