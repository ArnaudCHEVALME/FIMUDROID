package com.fimu.app.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fimu.app.database.models.TypeNews

@Dao
interface TypeNewsDao {
    @Query("SELECT * FROM types_news")
    fun getAll(): List<TypeNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(types_news: List<TypeNews>)
}
