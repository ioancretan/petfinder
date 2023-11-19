package com.ioan.animals.main

import android.app.Application
import com.ioan.animals.di.AnimalsAppComponent
import com.ioan.animals.di.DaggerAnimalsAppComponent
import com.ioan.animals.presentation.animalList.di.DaggerGetAnimalsComponent
import com.ioan.animals.presentation.animalList.di.GetAnimalsComponent

class AnimalsApplication : Application() {

    lateinit var animalsAppComponent: AnimalsAppComponent
    lateinit var getAnimalsComponent: GetAnimalsComponent

    override fun onCreate() {
        super.onCreate()
        animalsAppComponent = DaggerAnimalsAppComponent.factory().create(this)
        getAnimalsComponent = DaggerGetAnimalsComponent.factory().create(this)
        animalsAppComponent.inject(this)
    }
}