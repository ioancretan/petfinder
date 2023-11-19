package com.ioan.animals.presentation.animalList.di

import com.ioan.animals.data.animalList.AnimalsApi
import com.ioan.animals.data.animalList.AnimalsListDataStore
import com.ioan.animals.domain.animalList.AnimalsRepository
import com.ioan.animals.domain.animalList.GetAnimalsUS
import com.ioan.animals.domain.animalList.IGetAnimalsUS
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class GetAnimalsModule {

    @Singleton
    @Provides
    fun providesCountriesApi(retrofit: Retrofit): AnimalsApi {
        return retrofit.create(AnimalsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAnimalsRepository(api: AnimalsApi): AnimalsRepository {
        return AnimalsListDataStore(api)
    }

    @Singleton
    @Provides
    fun providesGetAnimalsUS(animalsRepository: AnimalsRepository): IGetAnimalsUS {
        return GetAnimalsUS(animalsRepository)
    }
}