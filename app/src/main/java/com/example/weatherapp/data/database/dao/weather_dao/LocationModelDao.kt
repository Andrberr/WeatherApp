package com.example.weatherapp.data.database.dao.weather_dao

import androidx.room.*
import com.example.weatherapp.data.database.entities.LocationModelEntity

@Dao
interface LocationModelDao {
    @Query("SELECT * FROM location_info_table")
    fun getAll(): LocationModelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationModelEntity)

    @Query("DELETE FROM location_info_table")
    fun delete()
}