package com.ioan.animals.data.animalList

import com.ioan.animals.domain.animalList.AnimalsRepository
import io.reactivex.Single
import javax.inject.Inject

class AnimalsListDataStore @Inject constructor(
    private val animalsApi: AnimalsApi
) : AnimalsRepository {

    override fun getAnimals(page: Int, location: String): Single<AnimalsResponse> {
        return animalsApi.getAnimals(page, location)
    }
}