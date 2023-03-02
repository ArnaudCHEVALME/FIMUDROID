package com.example.fimudroid.network

import com.example.fimudroid.models.Actualite
import com.example.fimudroid.models.Categorie
import com.example.fimudroid.models.Genre
import com.example.fimudroid.models.Pays
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "http://10.0.2.2:3000"
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface FimuApiService {


    @GET("actualite")
    suspend fun getNews(): List<Actualite>

    @GET("categorie")
    suspend fun getCategories():List<Categorie>

    @GET("genre")
    suspend fun getGenres() : List<Genre>

    @GET("pays")
    suspend fun getPays(): List<Pays>
}

object FimuApi {
    val retrofitService: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }
}