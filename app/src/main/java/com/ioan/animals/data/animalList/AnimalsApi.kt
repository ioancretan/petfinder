package com.ioan.animals.data.animalList


import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query

interface AnimalsApi {

    @GET("animals")
    fun getAnimals(
        @Query("page") page: Int,
        @Query("location") location: String
    ): Single<AnimalsResponse>
}