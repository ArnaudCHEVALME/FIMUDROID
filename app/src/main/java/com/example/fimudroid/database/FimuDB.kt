package com.example.fimudroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fimudroid.database.daos.*
import com.example.fimudroid.database.models.*

@Database(
    entities = [
        Artiste::class,
        Categorie::class,
        CategorieLien::class,
        Concert::class,
        Genre::class,
        Lien::class,
        News::class,
        Notification::class,
        Pays::class,
        ReseauSocial::class,
        Saison::class,
        Scene::class,
        Service::class,
        Stand::class,
        TypeNews::class,
        TypeScene::class,
        TypeStand::class,
    ], version = 1, exportSchema = false
)
abstract class FimuDB : RoomDatabase() {
    abstract fun artisteDao(): ArtisteDao
    abstract fun categoriesDao(): CategorieDao
    abstract fun categoriesLienDao(): CategorieLienDao
    abstract fun concertDao(): ConcertDao
    abstract fun genreDao(): GenreDao
    abstract fun lienDao(): LienDao
    abstract fun newsDao(): NewsDao
    abstract fun notificationDao(): NotificationDao
    abstract fun paysDao(): PaysDao
    abstract fun reseauSocialDao(): ReseauSocialDao
    abstract fun saisonDao(): SaisonDao
    abstract fun sceneDao(): SceneDao
    abstract fun serviceDao(): ServiceDao
    abstract fun standDao(): StandDao
    abstract fun typeNewsDao(): TypeNewsDao
    abstract fun typeSceneDao(): TypeSceneDao
    abstract fun typeStandDao(): TypeStandDao

    companion object {
        @Volatile
        private var INSTANCE: FimuDB? = null

        fun getInstance(context: Context): FimuDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, FimuDB::class.java, "fimu-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}