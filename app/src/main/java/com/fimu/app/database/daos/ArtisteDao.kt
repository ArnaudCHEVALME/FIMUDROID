package com.fimu.app.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Artiste

// Define the DAOs
@Dao
interface ArtisteDao {
    @Query("SELECT * FROM artistes")
    fun getAll(): LiveData<List<Artiste>>

    @Query("SELECT * FROM artistes WHERE id = :idArtiste")
    fun getById(idArtiste:Int): List<Artiste>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artistes: List<Artiste>)
    
    @Query("SELECT * FROM artistes ORDER BY nom ASC")
    fun sortByNameAsc(): LiveData<List<Artiste>> {
        return getAll()
    }
    @Query("SELECT * FROM artistes ORDER BY nom DESC")
    fun sortByNameDesc(): LiveData<List<Artiste>> {
        return getAll()
    }

    @Query("SELECT * FROM artistes WHERE pays = :pays")
    fun filterByCountry(country: String): LiveData<List<Artiste>> {
        return getAll()
    }

    @Query("SELECT * FROM artistes WHERE categorie = :categorie")
    fun filterByCategory(category: String): LiveData<List<Artiste>> {
        return getAll()
    }

    @Query("SELECT * FROM artistes WHERE concerts.date = :date")
    fun filterByDay(date: String): LiveData<List<Artiste>> {
        return getAll()
    }

    @Query("SELECT * FROM artistes WHERE pays = :pays AND categorie = :categorie" +
            " AND concerts.date = :date")
    fun filterByCountryCategoryDay(country: String, category: String, date: String): LiveData<List<Artiste>> {
        return getAll()

    }

    fun isEmpty(): Boolean {
        return getAll().value.isNullOrEmpty()
    }


}