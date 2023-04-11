package com.example.data.repositories

import com.example.data.database.entities.CitiesEntity
import com.example.data.mappers.CitiesMapper
import com.example.data.sources.DataBaseSource
import com.example.data.sources.UserCitySource
import com.example.domain.repositories.CitiesRepository
import com.example.domain.models.AddedCityInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.*

class CitiesRepositoryImpl @Inject constructor(
    private val citiesMapper: CitiesMapper,
    private val dataBaseSource: DataBaseSource,
    private val userCitySource: UserCitySource,
) : CitiesRepository {

    override suspend fun getCities(cache: Boolean): List<String> {
        val dbCities = dataBaseSource.getCities()
        if (dbCities.isNotEmpty()) {
            return dbCities.map { citiesMapper(it) }
        }

        val cities = mutableListOf<String>()
        val entityCities = mutableListOf<CitiesEntity>()

        val database = FirebaseDatabase.getInstance().reference
        val dataSnapshot = suspendCoroutine { continuation ->
            database.child("City").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    continuation.resume(dataSnapshot)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            })
        }

        for (userSnapshot in dataSnapshot.children) {
            var city: String? = userSnapshot.getValue(String::class.java)
            city = city ?: ""
            cities.add(city)
            entityCities.add(CitiesEntity(city = city))
        }

        CoroutineScope(Dispatchers.IO).launch {
            dataBaseSource.insertCities(entityCities)
        }

        return cities
    }


    override suspend fun getAddedCitiesInfo(): List<AddedCityInfo> {
        return withContext(Dispatchers.IO) {
            val uniqueCities = dataBaseSource.getUniqueCities()
            val list = mutableListOf<AddedCityInfo>()
            uniqueCities.forEach {
                list.add(
                    AddedCityInfo(
                        city = it,
                        temperature = dataBaseSource.getTemperatureForCity(it)
                    )
                )
            }
            list
        }
    }

    override fun setUserCity(city: String) {
        userCitySource.setUserCity(city)
    }

    override fun getUserCity(): String = userCitySource.getUserCity()
}