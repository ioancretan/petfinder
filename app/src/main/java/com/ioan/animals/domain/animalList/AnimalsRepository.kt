package com.ioan.animals.domain.animalList

import com.ioan.animals.data.animalList.AnimalsResponse
import io.reactivex.Single

interface AnimalsRepository
 {
    fun getAnimals(page: Int, location: String): Single<AnimalsResponse>
}

