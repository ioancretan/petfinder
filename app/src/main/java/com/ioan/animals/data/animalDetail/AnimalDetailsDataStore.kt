package com.ioan.animals.data.animalDetail

import com.ioan.animals.domain.animalDetail.AnimalDetailsRepository

class AnimalDetailsDataStore: AnimalDetailsRepository {
    override fun getAnimalDetails(): AnimalDetails {
        return AnimalDetails("horse")
    }
}