package com.example.data.database.dao.weather_dao

import androidx.room.*
import com.example.data.database.entities.LocationModelEntity

@Dao
interface LocationModelDao {
    @Query("SELECT * FROM location_info_table")
    fun getAll(): LocationModelEntity

    @Query("SELECT * FROM location_info_table WHERE city = :needCity")
    fun getCityLocation(needCity: String): LocationModelEntity

    @Query("SELECT city FROM location_info_table")
    fun getUniqueCities(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: LocationModelEntity)

    @Query("DELETE FROM location_info_table")
    fun delete()

    @Query("DELETE FROM location_info_table WHERE city = :needCity")
    fun deleteCityLocation(needCity: String)
}