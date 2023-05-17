package com.example.fimudroid.database.daos

import androidx.room.*
import com.example.fimudroid.database.models.TypeStand

@Dao
interface TypeStandDao {
    @Query("SELECT * FROM types_stand")
    suspend fun getAll(): List<TypeStand>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(typesStand: List<TypeStand>)

    @Query("UPDATE types_stand SET showed = :showed WHERE libelle = :libelle")
    suspend fun updateShowed(libelle: kotlin.String, showed: Boolean)

    @Query("SELECT * FROM types_stand WHERE libelle = :libelle")
    fun getByName(libelle: String): TypeStand

    @Query("SELECT showed FROM types_stand WHERE libelle = :libelle")
    fun isShowed(libelle: String): Boolean
}
