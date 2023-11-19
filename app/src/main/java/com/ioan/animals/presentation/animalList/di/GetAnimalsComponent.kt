package com.ioan.animals.presentation.animalList.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ioan.animals.di.NetworkModule
import com.ioan.animals.presentation.animalList.AnimalsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, GetAnimalsModule::class])
interface GetAnimalsComponent {

    fun inject(mainActivity: AnimalsFragment) // for field inject property inside the MainActivity

    fun getMap(): Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): GetAnimalsComponent
    }
}