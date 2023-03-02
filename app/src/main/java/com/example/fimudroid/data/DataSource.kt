package com.example.fimudroid.data

import com.example.fimudroid.models.*
import com.example.fimudroid.models.Artiste
import com.example.fimudroid.network.FimuApi

class DataSource {


    fun LoadArtists(): List<Artiste> {
        return listOf<Artiste>(
            Artiste(
                1,
                "bio",
                Categorie(
                    1,
                    "HG",
                    "Jul"
                ), LoadGenre(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                loadPays(),
                "img.png",
                LoadLiens()
            ),
            Artiste(
                2,
                "bio2",
                Categorie(
                    1,
                    "HG",
                    "Jul"
                ), LoadGenre(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                loadPays(),
                "img.png",
                LoadLiens()
            )


        )
    }

    fun LoadGenre(): List<Genre> {
        return listOf<Genre>(
            Genre(1, "Omelette"),
            Genre(2, "chat"),
            Genre(3, "chien"),
        )
    }

    fun loadPays(): List<Pays>? {
        return FimuApi.retrofitService.getPays().execute().body()
    }

    fun LoadLiens(): List<Lien> {
        return listOf<Lien>(
            Lien(LoadCategories()[0], "www.intenet.com"),
            Lien(LoadCategories()[1], "www.POPILE.com"),
        )
    }

    fun LoadCategories(): List<LienCategorie> {
        return listOf<LienCategorie>(
            LienCategorie(1, "lama", "logo"),
            LienCategorie(2, "chèvre", "logo"),
        )
    }

}