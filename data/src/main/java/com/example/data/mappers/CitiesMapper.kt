package com.example.data.mappers

import com.example.data.database.entities.CitiesEntity
import javax.inject.Inject

class CitiesMapper @Inject constructor() {
   operator fun invoke(entity: CitiesEntity): String = entity.city
}