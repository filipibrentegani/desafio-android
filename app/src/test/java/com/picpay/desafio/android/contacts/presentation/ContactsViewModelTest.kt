package com.picpay.desafio.android.contacts.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.any
import com.picpay.desafio.android.R
import com.picpay.desafio.android.contacts.domain.ContactsUseCase
import com.picpay.desafio.android.network.ResultWrapper
import com.picpay.desafio.android.ui.io
import com.picpay.desafio.android.ui.ui
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ContactsViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var savedStateHandleMock: SavedStateHandle

    @Mock
    lateinit var useCaseMock: ContactsUseCase

    val loadingMutableLiveData = MutableLiveData<Boolean>()

    private val viewModel: ContactsViewModel by lazy {
        ContactsViewModel(savedStateHandleMock)
    }

    @Before
    fun setUp() {
        io = Dispatchers.Unconfined
        ui = Dispatchers.Unconfined
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(
                module {
                    factory { useCaseMock }
                })
        }
        Mockito.`when`(savedStateHandleMock.getLiveData<Boolean>(any()))
            .thenReturn(loadingMutableLiveData)
    }

    @After
    fun clean() {
        stopKoin()
    }

    @Test
    fun requestUpdateCachedValuesAndHaveSuccess() {
        runBlockingTest {
            //given
            Mockito.`when`(useCaseMock.updateCachedValues()).thenReturn(
                ResultWrapper.Success(listOf())
            )
            //when
            viewModel.getContactData()
            //then
            Mockito.verify(useCaseMock).updateCachedValues()
            assertNull(viewModel.showError.value)
        }
    }

    @Test
    fun requestUpdateCachedValuesAndHaveError() {
        runBlockingTest {
            //given
            Mockito.`when`(useCaseMock.updateCachedValues()).thenReturn(
                ResultWrapper.Error(Exception("error!"))
            )
            //when
            viewModel.getContactData()
            //then
            Mockito.verify(useCaseMock).updateCachedValues()
            assertEquals(R.string.error, viewModel.showError.value)
        }
    }
}