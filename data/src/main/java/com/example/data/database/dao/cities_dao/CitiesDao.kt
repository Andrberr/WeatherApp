package com.example.data.database.dao.cities_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.CitiesEntity

@Dao
interface CitiesDao {
    @Query("SELECT * FROM cities_table")
    fun getAll(): List<CitiesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cities: List<CitiesEntity>)

    @Query("DELETE FROM cities_table")
    fun deleteAll()
}