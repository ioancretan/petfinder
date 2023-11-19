package com.ioan.animals

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ioan.animals.data.animalList.AnimalsResponse
import com.ioan.animals.data.animalList.Pagination
import com.ioan.animals.domain.animalList.GetAnimalsUS
import com.ioan.animals.presentation.animalList.AnimalsViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AnimalsViewModelsTests {

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: AnimalsViewModel
    lateinit var getAnimalsUS: GetAnimalsUS

    @Before
    fun before(){
        getAnimalsUS = mock(GetAnimalsUS::class.java)

        viewModel = AnimalsViewModel(getAnimalsUS)
    }

    @Test
    fun shouldCall_handleError_when_fetchAnimals_fails() {

        val viewModelSpy = spy(viewModel)
        `when`(getAnimalsUS.getAnimals(anyOrNull())).thenReturn(Single.error(Exception()))
        viewModelSpy.fetchAnimalsNextPage()

        verify(viewModelSpy, times(1)).handleError(any<Throwable>())
    }

    @Test
    fun shouldCall_handleResults_when_fetchAnimals_succes() {

        val viewModelSpy = spy(viewModel)

        `when`(getAnimalsUS.getAnimals(anyOrNull())).thenReturn(
            Single.just(
                AnimalsResponse(
                    emptyList(),
                    Pagination(0, 0, 0, 0)
                )
            )
        )

        viewModelSpy.fetchAnimalsNextPage()

        verify(viewModelSpy, times(1)).handleResults(anyOrNull())
    }
}