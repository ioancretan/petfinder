package com.ioan.animals.presentation.animalList

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ioan.animals.data.animalList.Animal
import com.ioan.animals.data.animalList.AnimalsResponse
import com.ioan.animals.domain.animalList.GetAnimalsUS
import com.ioan.animals.domain.animalList.IGetAnimalsUS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnimalsViewModel @Inject constructor(
    private val getAnimalsUS: GetAnimalsUS
) : ViewModel() {

    var longitude: Double = 0.0
    var latitude: Double = 0.0

    var currentPage = 1

    private val _animalsLiveData = MutableLiveData<List<Animal>>() // private member only
    val animalsLiveData: LiveData<List<Animal>> // public and read only.
        get() = _animalsLiveData


    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun fetchAnimalsNextPage() {
        loading.value = true
        getAnimalsUS.getAnimals(IGetAnimalsUS.Params(currentPage, longitude, latitude))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResults, this::handleError)
    }

    //TODO("Not yet implemented")
    fun handleError(throwable: Throwable?) {
        loading.value = false
    }

    fun handleResults(animalsResponse: AnimalsResponse) {
        currentPage++
        _animalsLiveData.value = animalsResponse.animals
        loading.value = false
    }
}