package com.example.weatherapp.data.database.dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.LocationModelEntity

@Dao
interface LocationModelDao {
    @Query("SELECT * FROM location_info_table")
    fun getAll(): LocationModelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationModelEntity)

    @Delete
    fun delete(location: LocationModelEntity)
}