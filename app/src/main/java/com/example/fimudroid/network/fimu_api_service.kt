package com.example.fimudroid.network

import com.example.fimudroid.models.Pays
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "http://localhost::3000"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(getHttpClient())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

private fun getHttpClient(): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
    okHttpBuilder.addInterceptor { chain ->
        val requestWithUserAgent = chain.request().newBuilder()
            .header("User-Agent", "My custom user agent")
            .build()
        chain.proceed(requestWithUserAgent)
    }
    return okHttpBuilder.build()
}

interface FimuApiService {
    @GET("pays")
    fun getPays():Call<List<Pays>>
}

object FimuApi {
    val retrofitService: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }
}