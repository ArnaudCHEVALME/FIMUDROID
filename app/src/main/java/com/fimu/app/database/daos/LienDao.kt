package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.Lien

@Dao
interface LienDao {

    @Query("SELECT * FROM liens")
    fun getAll(): List<Lien>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(liens: List<Lien>)
}
