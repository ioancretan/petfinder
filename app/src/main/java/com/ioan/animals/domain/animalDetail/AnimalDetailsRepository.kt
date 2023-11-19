package com.ioan.animals.domain.animalDetail

import com.ioan.animals.data.animalDetail.AnimalDetails

interface AnimalDetailsRepository {

    fun getAnimalDetails(): AnimalDetails
}