package com.example.fimudroid.data

import com.example.fimudroid.models.*
import com.example.fimudroid.models.Artiste

class DataSource {
    fun LoadActu(): List<Actualite> {
        return listOf<Actualite>(
            Actualite(
                "j'aime manger des enfants",
                "22/10/2022",
                1,
                1,
                "Ma passion!",
                TypeActu(
                    1,
                    "Important"
                )
            )
        )
    }

    fun LoadArtists(): List<Artiste> {
        return listOf<Artiste>(
            Artiste(
                1,
                "bio",
                Categorie(
                    "HG",
                    1,
                    "Jul"
                ), LoadGenre(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                LoadPays(),
                "img.png",
                LoadLiens()
            ),
            Artiste(
                2,
                "bio2",
                Categorie(
                    "HG",
                    1,
                    "Jul"
                ), LoadGenre(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                LoadPays(),
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

    fun LoadPays(): List<Pays> {
        return listOf<Pays>(
            Pays(1, "Shreklambourg"),
            Pays(2, "ArnaudCity"),
            Pays(3, "ChonkMael")
        )
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