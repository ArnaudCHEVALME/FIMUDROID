package com.example.fimudroid.data

import com.example.fimudroid.models.*
import com.example.fimudroid.models.Artiste
import com.example.fimudroid.network.FimuApi

class DataSource {

    private var artistes: List<Artiste> = emptyList()
    private var categories: List<Categorie> = emptyList()
    private var pays: List<Pays> = emptyList()
    private var genres: List<Genre> = emptyList()
    private var news: List<News> = emptyList()
    private var stands: List<Stand> = emptyList()
    private var services : List<Service> = emptyList()
    private var typeStand : List<TypeStand> = emptyList()

    suspend fun fetchData(){
        artistes = FimuApi.retrofitService.getArtistes()
        categories = FimuApi.retrofitService.getCategories()
        pays = FimuApi.retrofitService.getPays()
        genres = FimuApi.retrofitService.getGenres()
        news = FimuApi.retrofitService.getNews()
        stands = FimuApi.retrofitService.getStands()
        services = FimuApi.retrofitService.getServices()
        typeStand = FimuApi.retrofitService.getTypesStand()
    }

    fun getServices() : List<Service>{
        return services
    }

    fun getTypesStand() : List<TypeStand>{
        return typeStand
    }

    fun getArtistes() : List<Artiste>{
        return artistes
    }
    fun getCategories() : List<Categorie>{
        return categories
    }
    fun getPays() : List<Pays>{
        return pays
    }
    fun getNews() : List<News>{
        return news
    }
    fun getStands() : List<Stand>{
        return stands
    }
    fun getGenres() : List<Genre>{
        return genres
    }

}

