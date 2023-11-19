package com.ioan.animals.domain.animalList

import javax.inject.Inject

interface IGetAnimalsUS {
    data class Params(
        val page: Int,
        val longitude: Double,
        val latitude: Double
    ){
        fun toLocation() =
            "$latitude, $longitude"
    }
}

class GetAnimalsUS  @Inject constructor(
    private val contentRepository: AnimalsRepository
) : IGetAnimalsUS {

    fun getAnimals(params: IGetAnimalsUS.Params) =
        contentRepository.getAnimals(params.page, params.toLocation())

}