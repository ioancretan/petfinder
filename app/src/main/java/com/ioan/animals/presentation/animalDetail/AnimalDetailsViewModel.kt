package com.ioan.animals.presentation.animalDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ioan.animals.data.animalDetail.AnimalDetails
import com.ioan.animals.domain.animalDetail.AnimalDetailsRepository

class AnimalDetailsViewModel constructor(
    private val repository: AnimalDetailsRepository
) : ViewModel() {

    private val _animalDetailsLiveData = MutableLiveData<AnimalDetails>() // private member only
    val animalDetailLiveData: LiveData<AnimalDetails> // public and read only.
        get() = _animalDetailsLiveData

    val animalDetailsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    private fun handleError(throwable: Throwable?) {
        TODO("Not yet implemented")
        loading.value = false
    }

    private fun handleResults(animalResponse: AnimalDetails) {
        _animalDetailsLiveData.value = animalResponse
        loading.value = false
    }
}