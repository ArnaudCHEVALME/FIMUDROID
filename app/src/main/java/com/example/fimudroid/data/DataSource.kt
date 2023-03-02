package com.example.fimudroid.data

import com.example.fimudroid.models.*
import com.example.fimudroid.models.Artiste

class DataSource {
    fun LoadActu(): List<Actualite> {
        return listOf<Actualite>(
            Actualite(
                1,
                "j'aime manger des enfants",
                "22/10/2022",
                1,
                "Ma passion!",
                TypeActu(
                    1,
                    "Important"
                )
            )
        )
    }
    fun loadArtists(): List<Artiste> {
        return listOf<Artiste>(
            Artiste(
                1,
                "bio",
                Categorie(
                    1,
                    "HG",
                    "Jul"
                ), loadGenres(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                loadPays(),
                "img.png",
                loadLiens()
            ),
            Artiste(
                2,
                "bio2",
                Categorie(
                    1,
                    "HG",
                    "Jul"
                ), loadGenres(),
                "baka.com",
                "baka.ytb",
                "BakaName",
                loadPays(),
                "img.png",
                loadLiens()
            )


        )
    }

    fun loadGenres(): List<Genre> {
        return listOf<Genre>(
            Genre(1, "Omelette"),
            Genre(2, "chat"),
            Genre(3, "chien"),
        )
    }

    fun loadPays(): List<Pays>
    {
        return  listOf<Pays>(
            Pays(1, "Shreklambourg"),
            Pays(2, "ArnaudCity"),
            Pays(3, "ChonkMael")
        )
    }
    fun loadLiens(): List<Lien> {
        return listOf<Lien>(
            Lien(loadCategories()[0], "www.intenet.com"),
            Lien(loadCategories()[1], "www.POPILE.com"),
        )
    }

    fun loadCategories(): List<LienCategorie> {
        return listOf<LienCategorie>(
            LienCategorie(1, "lama", "logo"),
            LienCategorie(2, "chèvre", "logo"),
        )
    }

}