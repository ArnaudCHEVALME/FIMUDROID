package com.example.fimudroid.network

import com.example.fimudroid.network.models.*
import com.example.fimudroid.network.reponses.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "http://192.168.86.17:3000"
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface FimuApiService {

    @GET("scene")
    suspend fun getScenes(): SceneListResponse

    @GET("actualite")
    suspend fun getNews(): NewsListResponse

    @GET("actualite/{id}")
    suspend fun getNewsById(@Path("id") id: Int): NewsResponse

    @GET("typeactu")
    suspend fun getTypesNews(): TypeNewsListResponse

    @GET("categorie")
    suspend fun getCategories(): CategorieListResponse

    @GET("genre")
    suspend fun getGenres() : GenreListResponse

    @GET("pays")
    suspend fun getPays(): PaysListResponse

    @GET("artiste")
    suspend fun getArtistes(): ArtisteListResponse

    @GET("artiste/{id}")
    suspend fun getArtisteById(@Path("id") id: Int): ArtisteResponse

    @GET("stand")
    suspend fun getStands(): StandListResponse

    @GET("typestand")
    suspend fun getTypesStand(): TypeStandListResponse

    @GET("service")
    suspend fun getServices(): ServiceListResponse

    @GET("status")
    suspend fun checkAPIStatus() : APIStatus

    @GET("concert")
    suspend fun getConcerts() : ConcertsListResponse

    @GET("concert/{id}")
    suspend fun getConcertById(@Path("id") id: Int) : ConcertResponse
}