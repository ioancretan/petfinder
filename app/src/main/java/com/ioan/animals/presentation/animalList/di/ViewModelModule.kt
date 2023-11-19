package com.ioan.animals.presentation.animalList.di

import androidx.lifecycle.ViewModel
import com.ioan.animals.presentation.animalList.AnimalsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(AnimalsViewModel::class)
    @IntoMap
    abstract fun  mainViewModel(animalsViewModel: AnimalsViewModel):ViewModel

}