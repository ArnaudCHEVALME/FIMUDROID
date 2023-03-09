package com.example.fimudroid.network

import com.example.fimudroid.network.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


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
    suspend fun getNews(): List<News>

    @GET("typeactu")
    suspend fun getTypesNews(): List<TypeNews>

    @GET("categorie")
    suspend fun getCategories():List<Categorie>

    @GET("genre")
    suspend fun getGenres() : List<Genre>

    @GET("pays")
    suspend fun getPays(): List<Pays>

    @GET("artiste")
    suspend fun getArtistes(): List<Artiste>

    @GET("stand")
    suspend fun getStands(): List<Stand>

    @GET("typestand")
    suspend fun getTypesStand(): List<TypeStand>

    @GET("service")
    suspend fun getServices(): List<Service>


}